package com.mutsuddi_s.starwars.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mutsuddi_s.starwars.data.model.people.CharacterRemoteKeys

@Dao
interface CharacteRemoteKeysDao {
    @Query("SELECT * FROM character_remote_keys WHERE name=:name")
    suspend fun getRemoteKeys(name: String): CharacterRemoteKeys

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllRemoteKeys(characters: List<CharacterRemoteKeys>)


    @Query("DELETE FROM character_remote_keys")
    suspend fun deleteAllCharacterKeys()
}