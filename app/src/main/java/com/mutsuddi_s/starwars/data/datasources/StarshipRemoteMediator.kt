package com.mutsuddi_s.starwars.data.datasources

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.mutsuddi_s.starwars.data.local.AppDatabase
import com.mutsuddi_s.starwars.data.model.planets.PlanetRemoteKeys
import com.mutsuddi_s.starwars.data.model.planets.Planets
import com.mutsuddi_s.starwars.data.model.starships.Starships
import com.mutsuddi_s.starwars.data.model.starships.StarshipsRemoteKeys
import com.mutsuddi_s.starwars.data.remote.ApiInterface
@OptIn(ExperimentalPagingApi::class)
class StarshipRemoteMediator(
    private val apiInterface: ApiInterface,
    private val appDatabase: AppDatabase
) : RemoteMediator<Int, Starships>() {

    // Access the Character DAO and CharacterRemoteKeys DAO from the Room database.
    private val starshipsDao = appDatabase.starshipsDao()
    private val starshipsRemoteKeysDao = appDatabase.starshipsRemoteKeysDao()

    /**
     * Loads data from the remote API based on the load type (refresh, prepend, or append).
     *
     * @param loadType The type of data load (refresh, prepend, or append).
     * @param state The current paging state.
     * @return A [MediatorResult] indicating the result of the data loading operation.
     */
    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, Starships>
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
            val response = apiInterface.getStarships(currentPage)
            val data = response.body()!!.results ?: emptyList()
            val endOfPaginationReached=data.isEmpty()

            val prevPage = if (currentPage == 1) null else currentPage - 1
            val nextPage = if (endOfPaginationReached) null else currentPage + 1

            // Save the characters and remoteKeys data into the local database.
            appDatabase.withTransaction {

                if (loadType == LoadType.REFRESH) {
                    starshipsDao.deleteAllPlanet()
                    starshipsRemoteKeysDao.deleteAllStarshipKeys()
                }
                starshipsDao.insertAll(data)

                val keys = response.body()?.results?.map { planet ->
                    StarshipsRemoteKeys(
                        name = planet.name,
                        prevPage = prevPage,
                        nextPage = nextPage
                    )


                }
                starshipsRemoteKeysDao.insertAllRemoteKeys(keys!!)


            }
            MediatorResult.Success(endOfPaginationReached)

        } catch (e: Exception) {
            MediatorResult.Error(e)

        }


    }

    /**
     * Get the remote key closest to the current position in the PagingState.
     *
     * @param state The current paging state.
     * @return The [PlanetRemoteKeys] associated with the closest item to the current position.
     */

    private suspend fun getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Int, Starships>
    ): StarshipsRemoteKeys? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.name?.let { name ->
                starshipsRemoteKeysDao.getRemoteKeys(name)
            }
        }
    }


    /**
     * Get the remote key for the first item in the PagingState.
     *
     * @param state The current paging state.
     * @return The [PlanetRemoteKeys] associated with the first item in the list.
     */

    private suspend fun getRemoteKeyForFirstItem(
        state: PagingState<Int, Starships>
    ): StarshipsRemoteKeys? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.let { planet ->
                starshipsRemoteKeysDao.getRemoteKeys(name = planet.name)
            }
    }

    /**
     * Get the remote key for the last item in the PagingState.
     *
     * @param state The current paging state.
     * @return The [PlanetRemoteKeys] associated with the last item in the list.
     */

    private suspend fun getRemoteKeyForLastItem(
        state: PagingState<Int, Starships>
    ): StarshipsRemoteKeys? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let { planet ->
                starshipsRemoteKeysDao.getRemoteKeys(name = planet.name)
            }
    }
}