package com.mutsuddi_s.starwars.ui.characterdetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import androidx.paging.ExperimentalPagingApi
import com.mutsuddi_s.starwars.data.model.people.FilmResponse
import com.mutsuddi_s.starwars.data.model.people.HomeWorldResponse
import com.mutsuddi_s.starwars.data.model.people.SpeciesResponse
import com.mutsuddi_s.starwars.data.repository.StarWarsRepository
import com.mutsuddi_s.starwars.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@ExperimentalPagingApi
@HiltViewModel
class CharacterDetailsViewModel @OptIn(ExperimentalPagingApi::class)
@Inject constructor(private val charactersRepository: StarWarsRepository) :
    ViewModel() {
    private val _homeWorldLiveData = MutableLiveData<Resource<HomeWorldResponse>>()
    val homeWorldLiveData: LiveData<Resource<HomeWorldResponse>> = _homeWorldLiveData

    private val _films = MutableLiveData<Resource<List<FilmResponse>>>()
    val films: LiveData<Resource<List<FilmResponse>>> = _films

    private val _species = MutableLiveData<Resource<List<SpeciesResponse>>>()
    val species: LiveData<Resource<List<SpeciesResponse>>> = _species

    fun fetchHomeWorld(url: String) {
        viewModelScope.launch {
            _homeWorldLiveData.value = charactersRepository.getHomeWorld(url)
        }
    }

    fun fetchFilms(urls: List<String>) {
        viewModelScope.launch {

            val resultList = charactersRepository.getFilms(urls)
            _films.value = resultList

        }
    }

    fun fetchSpecies(urls: List<String>) {
        viewModelScope.launch {

            val resultList = charactersRepository.getSpecies(urls)
            _species.value = resultList

        }
    }


}