package com.mutsuddi_s.starwars.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mutsuddi_s.starwars.data.model.planets.PlanetRemoteKeys

@Dao
interface PlanetsRemoteKeysDao {

    @Query("SELECT * FROM planets_remote_keys WHERE name=:name")
    suspend fun getRemoteKeys(name: String): PlanetRemoteKeys

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllRemoteKeys(planets: List<PlanetRemoteKeys>)


    @Query("DELETE FROM planets_remote_keys")
    suspend fun deleteAllPlanetKeys()
}