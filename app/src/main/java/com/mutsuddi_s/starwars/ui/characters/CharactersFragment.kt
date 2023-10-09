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
import kotlinx.coroutines.launch

@ExperimentalPagingApi
@AndroidEntryPoint
class CharactersFragment : Fragment(),CharactersAdapter.OnItemClickListener{

    private var _binding: FragmentCharactersBinding? = null
    private val binding get() = _binding!!
    private val viewModel: CharactersViewModel by viewModels()
    private lateinit var charactersAdapter: CharactersAdapter


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
        charactersAdapter = CharactersAdapter(this)
        setupRecyclerView()
        setupObservers("")
        binding.searchView.onQueryTextChanged {
            setupObservers(it)
           // binding.charactersProgressBar.isVisible = true
            hideKeyboard()
        }




    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupObservers(searchString: String) {
        lifecycleScope.launch {
            viewModel.getCharacters(searchString).observe(viewLifecycleOwner) {
                charactersAdapter.submitData(lifecycle, it)
                //it?.let { binding.charactersProgressBar.isVisible=false }
            }
        }
    }

    private fun setupRecyclerView() {


        binding.charactersRecyclerview.apply {
            adapter = charactersAdapter
            setHasFixedSize(true)
            adapter=charactersAdapter.withLoadStateHeaderAndFooter(
                header = LoaderAdapter(),
                footer = LoaderAdapter(),
            )


        }



        charactersAdapter.addLoadStateListener { loadState ->
            if (loadState.refresh is LoadState.Loading) {
                binding.charactersProgressBar.isVisible = charactersAdapter.snapshot().isEmpty()
                //binding.textViewError.isVisible = false

            } else {

                val error = when {
                    loadState.prepend is LoadState.Error -> loadState.prepend as LoadState.Error
                    loadState.append is LoadState.Error -> loadState.append as LoadState.Error
                    loadState.refresh is LoadState.Error -> loadState.refresh as LoadState.Error

                    else -> null
                }
                error?.let {
                    if (charactersAdapter.snapshot().isEmpty()) {
                     //   binding.textViewError.isVisible = true
                      //  binding.textViewError.text = it.error.message
                        binding.charactersProgressBar.isVisible=false
                        val snack = Snackbar.make(binding.root, it.error.message.toString(),Snackbar.LENGTH_LONG)
                        snack.show()

                    }
                }
            }
        }


    }

    override fun onItemClick(character: Character) {
        val action=CharactersFragmentDirections.actionCharactersFragmentToCharacterDetailsFragment(character)
        Navigation.findNavController(binding.root).navigate(action)
    }


}