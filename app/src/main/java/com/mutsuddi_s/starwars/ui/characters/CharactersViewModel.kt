package com.mutsuddi_s.starwars.ui.characters

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.mutsuddi_s.starwars.data.model.people.Character
import com.mutsuddi_s.starwars.data.repository.StarWarsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * ViewModel class responsible for providing Star Wars character data to the UI layer.
 *
 * @param charactersRepository An instance of [StarWarsRepository] for retrieving character data.
 */

@ExperimentalPagingApi
@HiltViewModel
class CharactersViewModel
@Inject constructor(private val charactersRepository: StarWarsRepository) :
    ViewModel() {



    /**
     * Retrieves a LiveData stream of paged Star Wars character data based on a search string.
     *
     * @param searchString The search string used to filter characters by name.
     * @return A LiveData stream of paged Star Wars character data.
     */
    fun getCharacters(searchString: String): LiveData<PagingData<Character>> {
        // Use the [StarWarsRepository] to fetch and cache character data in the ViewModel's scope.
        return charactersRepository.getCharacters(searchString).cachedIn(viewModelScope)
    }

}