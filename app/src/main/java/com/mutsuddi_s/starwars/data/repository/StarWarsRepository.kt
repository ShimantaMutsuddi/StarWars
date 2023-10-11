package com.mutsuddi_s.starwars.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import com.mutsuddi_s.starwars.data.datasources.CharacterRemoteMediator
import com.mutsuddi_s.starwars.data.datasources.PlanetRemoteMediator
import com.mutsuddi_s.starwars.data.datasources.StarshipRemoteMediator
import com.mutsuddi_s.starwars.data.local.AppDatabase
import com.mutsuddi_s.starwars.data.model.people.FilmResponse
import com.mutsuddi_s.starwars.data.model.people.SpeciesResponse
import com.mutsuddi_s.starwars.data.remote.ApiInterface
import com.mutsuddi_s.starwars.data.remote.SafeApiCall
import com.mutsuddi_s.starwars.utils.Resource
import javax.inject.Inject
@ExperimentalPagingApi
class StarWarsRepository @Inject constructor(
    private val apiService: ApiInterface,
    private val appDatabase: AppDatabase
):SafeApiCall()  {


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

    /**
     * Retrieves the homeworld data for a Star Wars character using its URL.
     *
     * @param url The URL of the character's homeworld.
     * @return A Resource object representing the result of the operation.
     */

     suspend fun getHomeWorld(url: String) = safeApiCall {
        apiService.getHomeWorld(url)
    }

    /**
     * Retrieves the list of films associated with a character using a list of film URLs.
     *
     * @param urls The list of film URLs.
     * @return A Resource object representing the result of the operation.
     */
    suspend fun getFilms(urls: List<String>): Resource<List<FilmResponse>> {

        try {
            val films = mutableListOf<FilmResponse>()

            for (url in urls) {
                val film = apiService.getFilm(url)
                films.add(film)
            }

            return Resource.Success(films)
        } catch (e: Exception) {
            return Resource.Error(e.localizedMessage)
        }
    }

    /**
     * Retrieves the list of species associated with a character using a list of species URLs.
     *
     * @param urls The list of species URLs.
     * @return A Resource object representing the result of the operation.
     */
    suspend fun getSpecies(urls: List<String>): Resource<List<SpeciesResponse>> {

        try {
            val speciesList = mutableListOf<SpeciesResponse>()

            for (url in urls) {
                val species = apiService.getSpecies(url)
                speciesList.add(species)
            }

            return Resource.Success(speciesList)
        } catch (e: Exception) {
            return Resource.Error(e.localizedMessage)
        }
    }

    /**
     * Retrieves a LiveData stream of Star Wars planet data with paging support.
     *
     * @param searchString The search string to filter planets by name.
     * @return A LiveData stream of paged Star Wars planet data.
     */

    fun getPlanets(searchString: String) = Pager(
        config = PagingConfig(pageSize = 10, maxSize = 100),
        remoteMediator = PlanetRemoteMediator(apiService, appDatabase),
        pagingSourceFactory = { appDatabase.planetDao().getPlanetsByName(searchString) }

    ).liveData

    /**
     * Retrieves a LiveData stream of Star Wars starship data with paging support.
     *
     * @param searchString The search string to filter starships by name.
     * @return A LiveData stream of paged Star Wars starship data.
     */

    fun getStarships(searchString: String) = Pager(
        config = PagingConfig(pageSize = 10, maxSize = 100),
        remoteMediator = StarshipRemoteMediator(apiService, appDatabase),
        pagingSourceFactory = { appDatabase.starshipsDao().getStarshipsByName(searchString) }

    ).liveData
}