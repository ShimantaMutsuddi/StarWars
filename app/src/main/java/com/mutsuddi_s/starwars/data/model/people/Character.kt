package com.mutsuddi_s.starwars.data.model.people

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "characters")
data class Character(
    @PrimaryKey
    val name: String,
    val birth_year: String,
    val created: String,
    val edited: String,
    val eye_color: String,
    val gender: String,
    val hair_color: String,
    val height: String,
    val homeworld: String,
    val mass: String,
    val skin_color: String,
    val starships: List<String>,
    val species: List<String>,
    val films: List<String>,
    val url: String,
    val vehicles: List<String>
): Serializable