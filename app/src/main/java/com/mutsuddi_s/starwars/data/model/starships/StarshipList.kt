package com.mutsuddi_s.starwars.data.model.starships

data class StarshipList(
    val count: Int,
    val next: String,
    val previous: Any,
    val results: List<Starships>
)