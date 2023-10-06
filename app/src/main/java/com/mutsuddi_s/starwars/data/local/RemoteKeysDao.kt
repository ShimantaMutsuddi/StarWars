package com.mutsuddi_s.starwars.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mutsuddi_s.starwars.data.model.people.CharacterRemoteKeys

@Dao
interface RemoteKeysDao {
    @Query("SELECT * FROM CharacterRemoteKeys WHERE id=:id")
    suspend fun getRemoteKeys(id: Int): CharacterRemoteKeys

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllRemoteKeys(characters: List<CharacterRemoteKeys>)


    @Query("DELETE FROM CharacterRemoteKeys")
    suspend fun deleteAllCharacterKeys()
}