package com.mutsuddi_s.starwars.data.model.planets

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "planets")
data class Planets(
    val climate: String,
    val diameter: String,
    val edited: String,
    @PrimaryKey
    val name: String,
    val orbital_period: String,
    val population: String,
    val rotation_period: String
)