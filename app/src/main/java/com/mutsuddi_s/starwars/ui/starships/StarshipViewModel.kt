package com.mutsuddi_s.starwars.ui.starships

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.mutsuddi_s.starwars.data.model.planets.Planets
import com.mutsuddi_s.starwars.data.model.starships.Starships
import com.mutsuddi_s.starwars.data.repository.StarWarsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@ExperimentalPagingApi
@HiltViewModel
class StarshipViewModel @Inject constructor(private val repository: StarWarsRepository) :
    ViewModel(){

    fun getPlanets(searchString: String): LiveData<PagingData<Starships>> {
        // Use the [StarWarsRepository] to fetch and cache character data in the ViewModel's scope.
        return repository.getStarships(searchString).cachedIn(viewModelScope)
    }
}