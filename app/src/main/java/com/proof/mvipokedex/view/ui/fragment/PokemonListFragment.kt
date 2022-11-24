package com.proof.mvipokedex.view.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalContext
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.proof.mvipokedex.R
import com.proof.mvipokedex.domain.repository.Repository
import com.proof.mvipokedex.presentation.intent.PokemonIntent
import com.proof.mvipokedex.presentation.state.PokemonState
import com.proof.mvipokedex.presentation.viewModel.ViewModelFactoryPokemon
import com.proof.mvipokedex.presentation.viewModel.ViewModelPokemon
import com.proof.mvipokedex.view.compose.LoadingScreen
import com.proof.mvipokedex.view.compose.PokemonList
import com.proof.mvipokedex.view.ui.theme.MVIComposeTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class PokemonListFragment  : Fragment() {

    private lateinit var pokemonViewModel: ViewModelPokemon

    @Inject
    lateinit var viewModelFactoryPokemon: ViewModelFactoryPokemon

    @Inject
    lateinit var repository: Repository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        pokemonViewModel =
            ViewModelProvider(this, viewModelFactoryPokemon)[ViewModelPokemon::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = ComposeView(inflater.context).apply {
        layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
        setContent {
            MVIComposeTheme {
                when (val state = pokemonViewModel.state.value) {
                    is PokemonState.Loading -> LoadingScreen()
                    is PokemonState.PokemonList -> PokemonList(pokemons = state.pokemons,
                        vm = pokemonViewModel, onButtonClick = ::onPokemonClicked)
                    is PokemonState.Error -> {
                        Toast.makeText(LocalContext.current, state.error, Toast.LENGTH_SHORT).show()
                    }
                    else -> {}
                }
            }
        }
    }

    private fun onPokemonClicked(name: String) {
        activity?.let {
            findNavController().navigate(
                R.id.action_pokemonListFragment_to_pokemonDetailFragment,
                Bundle().apply {
                    putString("name", name)
                })
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sendIntent()
    }

    private fun sendIntent() {
        lifecycleScope.launch {
            pokemonViewModel.pokemonIntent.send(PokemonIntent.FetchPokemonList)
        }
    }

}