package com.mutsuddi_s.starwars.ui.characterdetails.util

import com.mutsuddi_s.starwars.data.model.people.FilmResponse
import com.mutsuddi_s.starwars.data.model.people.SpeciesResponse


object DataDummy {

    fun generateDummyFilmsItemEntity(): List<FilmResponse> {
        val listFilms = ArrayList<FilmResponse>()
        for (i in 1..5) {
            val films = FilmResponse(
                "It is a dark time for the\\r\\nRebellion",
                "The Empire Strikes Back",

            )
            listFilms.add(films)
        }
        return listFilms
    }

    fun generateDummySpeciesItemEntity(): List<SpeciesResponse> {
        val listSpecies = ArrayList<SpeciesResponse>()
        for (i in 1..5) {
            val species = SpeciesResponse(
                "Droid",
                "artificial",
                "sentient"

                )
            listSpecies.add(species)
        }
        return listSpecies
    }


}