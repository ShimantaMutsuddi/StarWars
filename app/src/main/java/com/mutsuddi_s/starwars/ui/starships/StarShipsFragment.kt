package com.mutsuddi_s.starwars.ui.starships

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.ExperimentalPagingApi

import com.mutsuddi_s.starwars.R
import com.mutsuddi_s.starwars.data.adapters.LoaderAdapter
import com.mutsuddi_s.starwars.data.adapters.PlanetsAdapter
import com.mutsuddi_s.starwars.data.adapters.StarshipAdapter
import com.mutsuddi_s.starwars.databinding.FragmentPlanetsBinding
import com.mutsuddi_s.starwars.databinding.FragmentStarShipsBinding
import com.mutsuddi_s.starwars.ui.planets.PlanetViewModel
import com.mutsuddi_s.starwars.utils.hideKeyboard
import com.mutsuddi_s.starwars.utils.onQueryTextChanged
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@ExperimentalPagingApi
@AndroidEntryPoint
class StarShipsFragment : Fragment() {
    private var _binding: FragmentStarShipsBinding? = null
    private val binding get() = _binding!!
    private val viewModel: StarshipViewModel by viewModels()
    private lateinit var starshipAdapter: StarshipAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentStarShipsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        starshipAdapter = StarshipAdapter()
        setupRecyclerView()
        setupObservers("")
        binding.searchView.onQueryTextChanged {
            setupObservers(it)
            binding.starshipProgressBar.isVisible = true
            hideKeyboard()
        }




    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupObservers(searchString: String) {
        lifecycleScope.launch {
            viewModel.getPlanets(searchString).observe(viewLifecycleOwner) {
                starshipAdapter.submitData(lifecycle, it)
                it?.let { binding.starshipProgressBar.isVisible=false }
            }
        }
    }

    private fun setupRecyclerView() {


        binding.starshipRecyclerview.apply {
            adapter = starshipAdapter
            setHasFixedSize(true)
            adapter=starshipAdapter.withLoadStateHeaderAndFooter(
                header = LoaderAdapter(),
                footer = LoaderAdapter(),
            )


        }


    }


}