package com.mutsuddi_s.starwars.ui.characters

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.mutsuddi_s.starwars.data.adapters.CharactersAdapter
import com.mutsuddi_s.starwars.data.adapters.LoaderAdapter
import com.mutsuddi_s.starwars.data.model.people.Character
import com.mutsuddi_s.starwars.databinding.FragmentCharactersBinding
import com.mutsuddi_s.starwars.utils.hideKeyboard
import com.mutsuddi_s.starwars.utils.onQueryTextChanged
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@ExperimentalPagingApi
@AndroidEntryPoint
class CharactersFragment : Fragment(),CharactersAdapter.OnItemClickListener{

    // View binding for the fragment layout
    private var _binding: FragmentCharactersBinding? = null
    private val binding get() = _binding!!

    // View model for character data
    private val viewModel: CharactersViewModel by viewModels()

    // Adapter for displaying characters
    private lateinit var charactersAdapter: CharactersAdapter

    // Job for handling character search
    private var searchJob: Job? = null



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentCharactersBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Initialize the characters adapter and set up the RecyclerView
        charactersAdapter = CharactersAdapter(this)
        setupRecyclerView()

        // Initialize the character data observation with an empty search string
        setupObservers("")

        // Handle character search with a delay
        binding.searchView.onQueryTextChanged { query ->
            searchJob?.cancel()

            searchJob = lifecycleScope.launch {
                delay(300)
                setupObservers(query)
                binding.charactersProgressBar.isVisible = true
                hideKeyboard()
            }
        }




    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    /**
     * Set up observers for character data.
     * @param searchString The search string to filter characters.
     */

    private fun setupObservers(searchString: String) {
        lifecycleScope.launch {
            viewModel.getCharacters(searchString).observe(viewLifecycleOwner) {
                charactersAdapter.submitData(lifecycle, it)
                it?.let { binding.charactersProgressBar.isVisible=false }

            }
        }
    }

    /**
     * Set up the RecyclerView with the characters adapter.
     */

    private fun setupRecyclerView() {


        binding.charactersRecyclerview.apply {
            adapter = charactersAdapter
            setHasFixedSize(true)
            adapter=charactersAdapter.withLoadStateFooter(
                footer = LoaderAdapter(),
            )


        }



        // Add a listener to handle load state changes and errors
        charactersAdapter.addLoadStateListener { loadState ->
            if (loadState.refresh is LoadState.Loading) {
                binding.charactersProgressBar.isVisible = charactersAdapter.snapshot().isEmpty()


            } else {

                val error = when {
                    loadState.prepend is LoadState.Error -> loadState.prepend as LoadState.Error
                    loadState.append is LoadState.Error -> loadState.append as LoadState.Error
                    loadState.refresh is LoadState.Error -> loadState.refresh as LoadState.Error

                    else -> null
                }
                error?.let {
                    if (charactersAdapter.snapshot().isEmpty()) {
                        binding.charactersProgressBar.isVisible=false
                        val snack = Snackbar.make(binding.root, it.error.message.toString(),Snackbar.LENGTH_LONG)
                        snack.show()

                    }
                }
            }
        }


    }

    /**
     * Handles item click event for characters, navigating to the character details screen.
     * @param character The selected character.
     */

    override fun onItemClick(character: Character) {
        val action=CharactersFragmentDirections.actionCharactersFragmentToCharacterDetailsFragment(character)
        Navigation.findNavController(binding.root).navigate(action)
    }


}