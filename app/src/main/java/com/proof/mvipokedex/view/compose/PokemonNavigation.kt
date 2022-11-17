package com.proof.mvipokedex.view.compose

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.proof.mvipokedex.presentation.viewModel.ViewModelPokemon

@Composable
fun PokemonNavigation(
    navHostController: NavHostController,
    pokemonViewModel: ViewModelPokemon,
    onButtonClick: () -> Unit
) {
    NavHost(navController = navHostController, startDestination = "mainScreen") {
        composable("mainScreen"){
            MainScreen(vm = pokemonViewModel, onButtonClick)
        }
//        composable(
//            "details/{pokemon}",
//            arguments = listOf(navArgument("pokemon"){type = NavType })
//        ){backStackEntry ->
//            backStackEntry.arguments?.getParcelable("pokemon")?.let {
//                PokemonDetail(pokemon = pokemon)
//            }
//        }
    }
}