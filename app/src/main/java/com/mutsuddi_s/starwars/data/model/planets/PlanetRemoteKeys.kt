package com.mutsuddi_s.starwars.data.model.planets

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "planets_remote_keys")
data class PlanetRemoteKeys (
    @PrimaryKey
    val name: String,
    val prevPage: Int?,
    val nextPage: Int?
)



