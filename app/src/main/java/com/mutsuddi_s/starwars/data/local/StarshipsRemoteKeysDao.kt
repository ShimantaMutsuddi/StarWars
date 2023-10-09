package com.mutsuddi_s.starwars.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mutsuddi_s.starwars.data.model.starships.StarshipsRemoteKeys
@Dao
interface StarshipsRemoteKeysDao {
    @Query("SELECT * FROM starships_remote_keys WHERE name=:name")
    suspend fun getRemoteKeys(name: String): StarshipsRemoteKeys

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllRemoteKeys(starships: List<StarshipsRemoteKeys>)


    @Query("DELETE FROM starships_remote_keys")
    suspend fun deleteAllStarshipKeys()
}