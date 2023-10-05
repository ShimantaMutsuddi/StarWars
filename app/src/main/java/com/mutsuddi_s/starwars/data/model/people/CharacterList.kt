package com.mutsuddi_s.starwars.data.model.people



data class CharacterList(
    val count: Int,
    val next: String,
    val previous: Any,
    val results: List<Character>
)