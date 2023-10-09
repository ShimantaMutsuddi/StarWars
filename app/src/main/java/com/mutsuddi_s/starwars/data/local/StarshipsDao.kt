package com.mutsuddi_s.starwars.data.local

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mutsuddi_s.starwars.data.model.starships.Starships

@Dao
interface StarshipsDao {
    @Query("SELECT * FROM starships WHERE name LIKE '%' || :searchQuery || '%'")
    fun getStarshipsByName(searchQuery: String): PagingSource<Int, Starships>
    @Query("SELECT * FROM starships")
    fun getPlanets(): PagingSource<Int, Starships>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(starships: List<Starships>)

    @Query("DELETE FROM starships")
    suspend fun deleteAllPlanet()
}