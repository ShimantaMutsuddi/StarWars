package com.mutsuddi_s.starwars.ui.characters

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.mutsuddi_s.starwars.data.model.people.Character
import com.mutsuddi_s.starwars.data.repository.StarWarsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class CharactersViewModel @Inject constructor(private val charactersRepository: StarWarsRepository) :
    ViewModel() {



    fun getCharacters(searchString: String): LiveData<PagingData<Character>> {
        return charactersRepository.getCharacters(searchString).cachedIn(viewModelScope)
    }

}