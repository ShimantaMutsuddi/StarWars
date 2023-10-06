package com.mutsuddi_s.starwars.data.model.people

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "CharacterRemoteKeys")
data class CharacterRemoteKeys(
    @PrimaryKey
    val id: Int,
    val prevPage: Int?,
    val nextPage: Int?


)