package com.mutsuddi_s.starwars.ui.characterdetails.util

import com.mutsuddi_s.starwars.data.model.people.Character
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

    fun generateDummyCharacterItemEntity(): List<Character> {
        val listCharacter = ArrayList<Character>()
        for (i in 1..5) {
            val character =Character(
                name = "Luke Skywalker",
                birth_year = "19 BBY",
                created = "2023-10-11T08:00:00Z",
                edited = "2023-10-11T08:00:00Z",
                eye_color = "Blue",
                gender = "Male",
                hair_color = "Blond",
                height = "172",
                homeworld = "Tatooine",
                mass = "77",
                skin_color = "Fair",
                starships = listOf("X-wing", "Millennium Falcon"),
                species = listOf("Human"),
                films = listOf("A New Hope", "The Empire Strikes Back", "Return of the Jedi"),
                url = "https://example.com/luke",
                vehicles = listOf("Snowspeeder"))
            listCharacter.add(character)
        }
        return listCharacter
    }


}