package com.mutsuddi_s.starwars.ui.characterdetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs

import com.mutsuddi_s.starwars.data.model.people.Character
import com.mutsuddi_s.starwars.databinding.FragmentCharacterDetailsBinding


class CharacterDetailsFragment : Fragment() {


    private var _binding: FragmentCharacterDetailsBinding? = null
    private val binding get() = _binding!!
    private val args: CharacterDetailsFragmentArgs by navArgs()
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

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}