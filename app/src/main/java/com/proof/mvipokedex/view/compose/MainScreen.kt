package com.proof.mvipokedex.view.compose

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.proof.mvipokedex.presentation.state.PokemonState
import com.proof.mvipokedex.presentation.viewModel.ViewModelPokemon


@Composable
fun MainScreen(vm: ViewModelPokemon, onButtonClick: () -> Unit) {
    val state = vm.state.value
    when (state) {
        is PokemonState.Loading -> LoadingScreen()
        is PokemonState.PokemonList -> PokemonList(pokemons = state.pokemons, vm = vm, onButtonClick)
        is PokemonState.PokemonInfo -> state.pokemonInfo?.let { PokemonDetail(pokemon = it) }
        is PokemonState.Error -> {
            Toast.makeText(LocalContext.current, state.error, Toast.LENGTH_SHORT).show()
        }
    }
}