package com.mutsuddi_s.starwars.ui.planets

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.mutsuddi_s.starwars.data.model.people.Character
import com.mutsuddi_s.starwars.data.model.planets.Planets
import com.mutsuddi_s.starwars.data.repository.StarWarsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@ExperimentalPagingApi
@HiltViewModel
class PlanetViewModel @Inject constructor(private val repository: StarWarsRepository) :
    ViewModel() {

    fun getPlanets(searchString: String): LiveData<PagingData<Planets>> {
        // Use the [StarWarsRepository] to fetch and cache character data in the ViewModel's scope.
        return repository.getPlanets(searchString).cachedIn(viewModelScope)
    }
}