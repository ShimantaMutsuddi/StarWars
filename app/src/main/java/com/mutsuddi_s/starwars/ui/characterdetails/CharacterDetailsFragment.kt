package com.mutsuddi_s.starwars.ui.characterdetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.LifecycleObserver
import androidx.navigation.fragment.navArgs
import androidx.paging.ExperimentalPagingApi
import com.mutsuddi_s.starwars.data.adapters.FilmsAdapter
import com.mutsuddi_s.starwars.data.adapters.SpeciesAdapter

import com.mutsuddi_s.starwars.data.model.people.Character
import com.mutsuddi_s.starwars.databinding.FragmentCharacterDetailsBinding
import com.mutsuddi_s.starwars.ui.characters.CharactersViewModel
import com.mutsuddi_s.starwars.utils.Resource
import dagger.hilt.android.AndroidEntryPoint

@ExperimentalPagingApi
@AndroidEntryPoint
class CharacterDetailsFragment : Fragment() {


    // View binding for the fragment layout
    private var _binding: FragmentCharacterDetailsBinding? = null
    private val binding get() = _binding!!

    // ViewModel for handling character details
    private val viewModel: CharacterDetailsViewModel by viewModels()

    // Arguments passed to this fragment, including the character to display
    private val args: CharacterDetailsFragmentArgs by navArgs()

    // Adapters for displaying films and species information
    private val filmsAdapter: FilmsAdapter by lazy {
        FilmsAdapter()
    }
    private val speciesAdapter: SpeciesAdapter by lazy {
        SpeciesAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentCharacterDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Display character details
        args.character?.let { bindCharacter(it) }

    }

    /**
     * Binds the provided character's information to the UI elements.
     *
     * @param character The character whose details are to be displayed.
     */
    private fun bindCharacter(character: Character) {
        binding.textViewFullNameValue.text = character.name
        binding.textViewSkinColorValue.text = character.skin_color
        binding.textViewHairColorValue.text = character.hair_color
        binding.textViewHeightValue.text = character.height
        binding.textViewMassValue.text = character.mass
        binding.textViewEyeColorValue.text = character.eye_color
        binding.textViewGenderValue.text = character.gender
        binding.textViewBirthYearValue.text = character.birth_year

        // Fetch and display the character's homeworld
        character.homeworld?.let { viewModel.fetchHomeWorld(it) }

        // Fetch and display the films
        character.films?.let {
            binding.filmsLayout.isVisible=true
            viewModel.fetchFilms(it)
        }

        // Fetch and display the species
        character.species?.let {
            binding.speciesLayout.isVisible=true
            viewModel.fetchSpecies(it)
        }

        // Observe and handle updates in the UI for homeworld information
        viewModel.homeWorldLiveData.observe(viewLifecycleOwner){ event ->
            when (event) {
                is Resource.Success -> {
                    binding.progressBarHomeWord.isVisible = false
                    binding.textViewHomeWorldValue.text = event.data!!.name
                }
                is Resource.Error -> {
                    binding.progressBarHomeWord.isVisible = false
                    binding.textViewHomeWorldValue.text = event.errorMessage
                    Toast.makeText(requireContext(), event.errorMessage, Toast.LENGTH_SHORT).show()
                }
                is Resource.Loading -> {
                    binding.progressBarHomeWord.isVisible = true
                }
                else -> Unit
            }
        }

        // Observe and handle updates in the UI for films information
        viewModel.films.observe(viewLifecycleOwner){ event ->
            when (event) {
                is Resource.Success -> {
                    binding.filmProgressBar.isVisible = false
                    filmsAdapter.submitList(event.data)
                    binding.recyclerViewFilms.adapter = filmsAdapter
                }
                is Resource.Error -> {
                    binding.filmProgressBar.isVisible = false
                    binding.textViewFilmsError.isVisible = true
                    binding.textViewFilmsError.text = event.errorMessage
                }
                is Resource.Loading -> {
                    binding.filmProgressBar.isVisible = true
                }
                else -> Unit
            }
        }

        // Observe and handle updates in the UI for species information
        viewModel.species.observe(viewLifecycleOwner){ event ->
            when (event) {
                is Resource.Success -> {
                    binding.SpeciesProgressBar.isVisible = false
                    speciesAdapter.submitList(event.data)
                    binding.recyclerViewSpecies.adapter = speciesAdapter
                }
                is Resource.Error -> {
                    binding.SpeciesProgressBar.isVisible = false
                    binding.textViewSpeciesError.isVisible = true
                    binding.textViewSpeciesError.text = event.errorMessage
                }
                is Resource.Loading -> {
                    binding.SpeciesProgressBar.isVisible = true
                }
                else -> Unit
            }
        }






    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}