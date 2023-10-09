package com.mutsuddi_s.starwars.data.model.starships

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("starships")
data class Starships(
    @PrimaryKey
    val name: String,
    val model: String


)