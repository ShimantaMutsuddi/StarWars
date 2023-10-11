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
import androidx.paging.LoadState
import com.google.android.material.snackbar.Snackbar

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
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@ExperimentalPagingApi
@AndroidEntryPoint
class StarShipsFragment : Fragment() {
    private var _binding: FragmentStarShipsBinding? = null
    private val binding get() = _binding!!
    private val viewModel: StarshipViewModel by viewModels()
    private lateinit var starshipAdapter: StarshipAdapter
    private var searchJob: Job? = null

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
        binding.searchView.onQueryTextChanged {query->

            searchJob?.cancel()


            searchJob = lifecycleScope.launch {
                delay(300)
                setupObservers(query)
                binding.starshipProgressBar.isVisible = true
                hideKeyboard()
            }
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

            starshipAdapter.addLoadStateListener { loadState ->
                if (loadState.refresh is LoadState.Loading) {
                    binding.starshipProgressBar.isVisible = starshipAdapter.snapshot().isEmpty()
                    //binding.textViewError.isVisible = false

                } else {

                    val error = when {
                        loadState.prepend is LoadState.Error -> loadState.prepend as LoadState.Error
                        loadState.append is LoadState.Error -> loadState.append as LoadState.Error
                        loadState.refresh is LoadState.Error -> loadState.refresh as LoadState.Error

                        else -> null
                    }
                    error?.let {
                        if (starshipAdapter.snapshot().isEmpty()) {
                            //   binding.textViewError.isVisible = true
                            //  binding.textViewError.text = it.error.message
                            binding.starshipProgressBar.isVisible=false
                            val snack = Snackbar.make(binding.root, it.error.message.toString(),
                                Snackbar.LENGTH_LONG)
                            snack.show()

                        }
                    }
                }
            }


        }


    }


}