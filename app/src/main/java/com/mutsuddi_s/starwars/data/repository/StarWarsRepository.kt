package com.mutsuddi_s.starwars.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import com.mutsuddi_s.starwars.data.datasources.CharactersPagingSource
import com.mutsuddi_s.starwars.data.remote.ApiInterface
import com.mutsuddi_s.starwars.data.remote.SafeApiCall
import com.mutsuddi_s.starwars.utils.Constants.NETWORK_PAGE_SIZE
import javax.inject.Inject

class StarWarsRepository @Inject constructor(private val apiService: ApiInterface) : SafeApiCall(){

    fun getCharacters(searchString: String) = Pager(
        config = PagingConfig(
            pageSize = NETWORK_PAGE_SIZE,
            enablePlaceholders = false
        ),
        pagingSourceFactory = {
            CharactersPagingSource(apiService, searchString)
        }
    ).liveData
}