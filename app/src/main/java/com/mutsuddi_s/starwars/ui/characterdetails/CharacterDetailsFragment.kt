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

import com.mutsuddi_s.starwars.data.model.people.Character
import com.mutsuddi_s.starwars.databinding.FragmentCharacterDetailsBinding
import com.mutsuddi_s.starwars.ui.characters.CharactersViewModel
import com.mutsuddi_s.starwars.utils.Resource
import dagger.hilt.android.AndroidEntryPoint

@ExperimentalPagingApi
@AndroidEntryPoint
class CharacterDetailsFragment : Fragment() {


    private var _binding: FragmentCharacterDetailsBinding? = null
    private val binding get() = _binding!!
    private val viewModel: CharacterDetailsViewModel by viewModels()
    private val args: CharacterDetailsFragmentArgs by navArgs()
    private val filmsAdapter: FilmsAdapter by lazy {
        FilmsAdapter()
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

        args.character?.let { bindCharacter(it) }

    }

    private fun bindCharacter(character: Character) {
        binding.textViewFullNameValue.text = character.name
        binding.textViewSkinColorValue.text = character.skin_color
        binding.textViewHairColorValue.text = character.hair_color
        binding.textViewHeightValue.text = character.height
        binding.textViewMassValue.text = character.mass
        binding.textViewEyeColorValue.text = character.eye_color
        binding.textViewGenderValue.text = character.gender
        binding.textViewBirthYearValue.text = character.birth_year

        character.homeworld?.let { viewModel.fetchHomeWorld(it) }
        character.films?.let {
            binding.filmsLayout.isVisible=true
            viewModel.fetchFilms(it)
        }



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






    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}