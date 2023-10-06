package com.mutsuddi_s.starwars.data.local


import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mutsuddi_s.starwars.data.model.people.Character

@Dao
interface CharacterDao {
    @Query("SELECT * FROM characters WHERE name LIKE '%' || :searchQuery || '%'")
    fun getCharacterByName(searchQuery: String): PagingSource<Int, Character>

    @Query("SELECT * FROM characters")
    fun getCharacter(): PagingSource<Int, Character>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(characters: List<Character>)

   @Query("DELETE FROM characters")
    suspend fun deleteAllCharacter()
/*
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(character: Character)

    @Query("DELETE FROM characters")
    suspend fun deleteAllCharacter()*/
}