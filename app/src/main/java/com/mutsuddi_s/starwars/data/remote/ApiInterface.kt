package com.mutsuddi_s.starwars.data.remote

import com.mutsuddi_s.starwars.data.model.people.CharacterList
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

interface ApiInterface {

    @GET("people/?page/")
    suspend fun getCharacters(@Query("page") page: Int): Response<CharacterList>


}