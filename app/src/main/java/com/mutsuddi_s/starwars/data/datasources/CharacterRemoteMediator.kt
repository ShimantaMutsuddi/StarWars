package com.mutsuddi_s.starwars.data.datasources


import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.mutsuddi_s.starwars.data.local.AppDatabase
import com.mutsuddi_s.starwars.data.remote.ApiInterface
import com.mutsuddi_s.starwars.data.model.people.Character
import com.mutsuddi_s.starwars.data.model.people.CharacterRemoteKeys
import retrofit2.HttpException
import java.io.IOException

/**
 * RemoteMediator class responsible for loading data from a remote data source
 * and saving it to a local Room database while providing paging functionality.
 *
 * @param characterService An instance of the remote API service.
 * @param appDatabase An instance of the local Room database.
 */
@OptIn(ExperimentalPagingApi::class)
class CharacterRemoteMediator(
    private val characterService: ApiInterface,
    private val appDatabase: AppDatabase
) : RemoteMediator<Int, Character>() {

    // Access the Character DAO and CharacterRemoteKeys DAO from the Room database.
    private val characterDao = appDatabase.characterDao()
    private val characterRemoteKeysDao = appDatabase.remoteKeysDao()

    /**
     * Loads data from the remote API based on the load type (refresh, prepend, or append).
     *
     * @param loadType The type of data load (refresh, prepend, or append).
     * @param state The current paging state.
     * @return A [MediatorResult] indicating the result of the data loading operation.
     */
    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, Character>
    ): MediatorResult {


        // Determine the current page based on the load type.
        return try {

            val currentPage = when (loadType) {
                LoadType.REFRESH -> {
                    val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                    remoteKeys?.nextPage?.minus(1) ?: 1
                }
                LoadType.PREPEND -> {
                    val remoteKeys = getRemoteKeyForFirstItem(state)
                    val prevPage = remoteKeys?.prevPage
                        ?: return MediatorResult.Success(
                            endOfPaginationReached = remoteKeys != null
                        )
                    prevPage
                }
                LoadType.APPEND -> {
                    val remoteKeys = getRemoteKeyForLastItem(state)
                    val nextPage = remoteKeys?.nextPage
                        ?: return MediatorResult.Success(
                            endOfPaginationReached = remoteKeys != null
                        )
                    nextPage
                }
            }
            // Fetch characters from the remote API.
            val response = characterService.getCharacters(currentPage)
            val data = response.body()!!.results ?: emptyList()
                val endOfPaginationReached=data.isEmpty()

            val prevPage = if (currentPage == 1) null else currentPage - 1
            val nextPage = if (endOfPaginationReached) null else currentPage + 1

            // Save the characters and remoteKeys data into the local database.
            appDatabase.withTransaction {

                if (loadType == LoadType.REFRESH) {
                    characterDao.deleteAllCharacter()
                    characterRemoteKeysDao.deleteAllCharacterKeys()
                }
                characterDao.insertAll(data)

                val keys = response.body()?.results?.map { character ->
                    CharacterRemoteKeys(
                        name = character.name,
                        prevPage = prevPage,
                        nextPage = nextPage
                    )


                }
                characterRemoteKeysDao.insertAllRemoteKeys(keys!!)


            }
            MediatorResult.Success(endOfPaginationReached)

        } catch (e: IOException) {
            MediatorResult.Error(e)

        } catch (e: HttpException) {
            // HttpException for any non-2xx HTTP status codes.
            return MediatorResult.Error(e)
        }


    }

    /**
     * Get the remote key closest to the current position in the PagingState.
     *
     * @param state The current paging state.
     * @return The [CharacterRemoteKeys] associated with the closest item to the current position.
     */

    private suspend fun getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Int, Character>
    ): CharacterRemoteKeys? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.name?.let { name ->
                characterRemoteKeysDao.getRemoteKeys(name)
            }
        }
    }


    /**
     * Get the remote key for the first item in the PagingState.
     *
     * @param state The current paging state.
     * @return The [CharacterRemoteKeys] associated with the first item in the list.
     */

    private suspend fun getRemoteKeyForFirstItem(
        state: PagingState<Int, Character>
    ): CharacterRemoteKeys? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.let { character ->
                characterRemoteKeysDao.getRemoteKeys(name = character.name)
            }
    }

    /**
     * Get the remote key for the last item in the PagingState.
     *
     * @param state The current paging state.
     * @return The [CharacterRemoteKeys] associated with the last item in the list.
     */

    private suspend fun getRemoteKeyForLastItem(
        state: PagingState<Int, Character>
    ): CharacterRemoteKeys? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let { character ->
                characterRemoteKeysDao.getRemoteKeys(name = character.name)
            }
    }
}