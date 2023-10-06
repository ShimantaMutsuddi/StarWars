package com.mutsuddi_s.starwars.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import com.mutsuddi_s.starwars.data.datasources.CharacterRemoteMediator
import com.mutsuddi_s.starwars.data.local.AppDatabase
import com.mutsuddi_s.starwars.data.remote.ApiInterface
import javax.inject.Inject
@ExperimentalPagingApi
class StarWarsRepository @Inject constructor(
    private val apiService: ApiInterface,
    private val appDatabase: AppDatabase
)  {

   /* fun getCharacters(searchString: String) = Pager(
        config = PagingConfig(
            pageSize = NETWORK_PAGE_SIZE,
            enablePlaceholders = false
        ),
        pagingSourceFactory = {
            CharactersPagingSource(apiService, searchString)
        }
    ).liveData*/
    /**
     * Retrieves a LiveData stream of Star Wars character data with paging support.
     *
     * @param searchString The search string to filter characters by name.
     * @return A LiveData stream of paged Star Wars character data.
     */

    fun getCharacters(searchString: String) = Pager(
        config = PagingConfig(pageSize = 10, maxSize = 100),
        remoteMediator = CharacterRemoteMediator(apiService, appDatabase),
        pagingSourceFactory = { appDatabase.characterDao().getCharacterByName(searchString) }

    ).liveData
}