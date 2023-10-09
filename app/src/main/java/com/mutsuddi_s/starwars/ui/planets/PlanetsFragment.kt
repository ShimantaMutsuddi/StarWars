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
import com.mutsuddi_s.starwars.data.adapters.LoaderAdapter
import com.mutsuddi_s.starwars.data.adapters.PlanetsAdapter
import com.mutsuddi_s.starwars.databinding.FragmentPlanetsBinding
import com.mutsuddi_s.starwars.utils.hideKeyboard
import com.mutsuddi_s.starwars.utils.onQueryTextChanged
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@ExperimentalPagingApi
@AndroidEntryPoint
class PlanetsFragment : Fragment() {
    private var _binding: FragmentPlanetsBinding? = null
    private val binding get() = _binding!!
    private val viewModel: PlanetViewModel by viewModels()
    private lateinit var planetAdapter: PlanetsAdapter

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
        planetAdapter = PlanetsAdapter()
        setupRecyclerView()
        setupObservers("")
        binding.searchView.onQueryTextChanged {
            setupObservers(it)
            binding.planetsProgressBar.isVisible = true
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
                planetAdapter.submitData(lifecycle, it)
                it?.let { binding.planetsProgressBar.isVisible=false }
            }
        }
    }

    private fun setupRecyclerView() {


        binding.planetsRecyclerview.apply {
            adapter = planetAdapter
            setHasFixedSize(true)
            adapter=planetAdapter.withLoadStateHeaderAndFooter(
                header = LoaderAdapter(),
                footer = LoaderAdapter(),
            )


        }


    }


}