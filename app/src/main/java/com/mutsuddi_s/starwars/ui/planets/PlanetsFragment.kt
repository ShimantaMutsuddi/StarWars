package com.mutsuddi_s.starwars.ui.planets

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
import com.mutsuddi_s.starwars.data.adapters.LoaderAdapter
import com.mutsuddi_s.starwars.data.adapters.PlanetsAdapter
import com.mutsuddi_s.starwars.databinding.FragmentPlanetsBinding
import com.mutsuddi_s.starwars.utils.hideKeyboard
import com.mutsuddi_s.starwars.utils.onQueryTextChanged
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@ExperimentalPagingApi
@AndroidEntryPoint
class PlanetsFragment : Fragment() {
    // ViewBinding for this fragment
    private var _binding: FragmentPlanetsBinding? = null
    private val binding get() = _binding!!

    // ViewModel for fetching planet data
    private val viewModel: PlanetViewModel by viewModels()

    // Adapter for displaying the list of planets
    private lateinit var planetAdapter: PlanetsAdapter

    // Coroutine job for handling search
    private var searchJob: Job? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentPlanetsBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Initialize the planetsAdapter for displaying planet data
        planetAdapter = PlanetsAdapter()

        // Set up the RecyclerView and load initial data
        setupRecyclerView()

        // Set up the search functionality
        setupObservers("")

        // Handle search input and start search coroutine
        binding.searchView.onQueryTextChanged { query ->
            searchJob?.cancel()

            searchJob = lifecycleScope.launch {
                delay(300)
                setupObservers(query)
                binding.planetsProgressBar.isVisible = true
                hideKeyboard()
            }
        }




    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    /**
     * Sets up observers for planet data and updates the RecyclerView accordingly.
     *
     * @param searchString The search string used to filter planet data.
     */
    private fun setupObservers(searchString: String) {
        lifecycleScope.launch {
            viewModel.getPlanets(searchString).observe(viewLifecycleOwner) {
                planetAdapter.submitData(lifecycle, it)
                it?.let { binding.planetsProgressBar.isVisible=false }
            }
        }
    }

    /**
     * Sets up the RecyclerView with the planetsAdapter and its LoadStateListener.
     */
    private fun setupRecyclerView() {


        binding.planetsRecyclerview.apply {
            adapter = planetAdapter
            setHasFixedSize(true)
            adapter=planetAdapter.withLoadStateFooter(
                footer = LoaderAdapter()
            )


        }
        planetAdapter.addLoadStateListener { loadState ->
            if (loadState.refresh is LoadState.Loading) {
                binding.planetsProgressBar.isVisible = planetAdapter.snapshot().isEmpty()
                //binding.textViewError.isVisible = false

            } else {

                val error = when {
                    loadState.prepend is LoadState.Error -> loadState.prepend as LoadState.Error
                    loadState.append is LoadState.Error -> loadState.append as LoadState.Error
                    loadState.refresh is LoadState.Error -> loadState.refresh as LoadState.Error

                    else -> null
                }
                error?.let {
                    if (planetAdapter.snapshot().isEmpty()) {
                        //   binding.textViewError.isVisible = true
                        //  binding.textViewError.text = it.error.message
                        binding.planetsProgressBar.isVisible=false
                        val snack = Snackbar.make(binding.root, it.error.message.toString(),
                            Snackbar.LENGTH_LONG)
                        snack.show()

                    }
                }
            }
        }


    }


}