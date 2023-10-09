package com.mutsuddi_s.starwars.data.local

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mutsuddi_s.starwars.data.model.planets.Planets

@Dao
interface PlanetsDao {

    @Query("SELECT * FROM planets WHERE name LIKE '%' || :searchQuery || '%'")
    fun getPlanetsByName(searchQuery: String): PagingSource<Int, Planets>
    @Query("SELECT * FROM planets")
    fun getPlanets(): PagingSource<Int, Planets>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(planets: List<Planets>)

    @Query("DELETE FROM planets")
    suspend fun deleteAllPlanet()
}