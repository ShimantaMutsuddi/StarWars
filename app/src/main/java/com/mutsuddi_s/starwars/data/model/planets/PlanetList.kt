package com.mutsuddi_s.starwars.data.model.planets

data class PlanetList(
    val count: Int,
    val next: String,
    val previous: Any,
    val results: List<Planets>
)