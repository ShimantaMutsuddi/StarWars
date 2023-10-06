package com.mutsuddi_s.starwars.data.model.people

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "CharacterRemoteKeys")
data class CharacterRemoteKeys(
    @PrimaryKey
    val name: String,
    val prevPage: Int?,
    val nextPage: Int?


)