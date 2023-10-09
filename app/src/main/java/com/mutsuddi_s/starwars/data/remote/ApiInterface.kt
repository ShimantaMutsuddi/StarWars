package com.mutsuddi_s.starwars.data.remote

import com.mutsuddi_s.starwars.data.model.people.CharacterList
import com.mutsuddi_s.starwars.data.model.people.FilmResponse
import com.mutsuddi_s.starwars.data.model.people.HomeWorldResponse
import com.mutsuddi_s.starwars.data.model.people.SpeciesResponse
import com.mutsuddi_s.starwars.data.model.planets.PlanetList
import com.mutsuddi_s.starwars.data.model.starships.StarshipList
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

interface ApiInterface {

    @GET("people/?page/")
    suspend fun getCharacters(@Query("page") page: Int): Response<CharacterList>

    @GET
    suspend fun getHomeWorld(@Url url: String): HomeWorldResponse

    @GET
    suspend fun getFilm(@Url url: String): FilmResponse

    @GET
    suspend fun getSpecies(@Url url: String): SpeciesResponse

    @GET("planets/?page/")
    suspend fun getPlanets(@Query("page") page: Int): Response<PlanetList>

    @GET("starships/?page/")
    suspend fun getStarships(@Query("page") page: Int): Response<StarshipList>


}