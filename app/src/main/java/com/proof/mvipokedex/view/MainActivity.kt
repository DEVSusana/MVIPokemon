package com.proof.mvipokedex.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.rememberNavController
import com.proof.mvipokedex.domain.repository.Repository
import com.proof.mvipokedex.presentation.intent.PokemonIntent
import com.proof.mvipokedex.presentation.viewModel.ViewModelFactoryPokemon
import com.proof.mvipokedex.presentation.viewModel.ViewModelPokemon
import com.proof.mvipokedex.view.compose.PokemonNavigation
import com.proof.mvipokedex.view.ui.theme.MVIComposeTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private lateinit var pokemonViewModel: ViewModelPokemon

    @Inject
    lateinit var viewModelFactoryPokemon: ViewModelFactoryPokemon

    @Inject
    lateinit var repository: Repository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        pokemonViewModel =
            ViewModelProvider(this, viewModelFactoryPokemon)[ViewModelPokemon::class.java]


        lifecycleScope.launch {
            pokemonViewModel.pokemonIntent.send(PokemonIntent.FetchPokemonList)
        }

        val onButtonClick: () -> Unit = {
            lifecycleScope.launch {
                pokemonViewModel.pokemonIntent.send(PokemonIntent.CheckPokemonInfo)
            }
        }

        setContent {
            MVIComposeTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    val navController = rememberNavController()
                    PokemonNavigation(navHostController = navController, pokemonViewModel = pokemonViewModel, onButtonClick)
                }
            }
        }
    }
}


