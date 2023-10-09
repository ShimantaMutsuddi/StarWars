package com.mutsuddi_s.starwars.data.model.starships

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "starships_remote_keys")
data class StarshipsRemoteKeys (
    @PrimaryKey
    val name: String,
    val prevPage: Int?,
    val nextPage: Int?
)