package com.mutsuddi_s.starwars.data.model.people

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "characters")
data class Character(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val birth_year: String,
    //val created: String,
   // val edited: String,
    val eye_color: String,
    val gender: String,
    val hair_color: String,
    val height: String,
    val homeworld: String,
    val mass: String,
    val name: String,
    val skin_color: String,
//    val starships: List<String>,
   // val url: String,
   // val vehicles: List<String>
)