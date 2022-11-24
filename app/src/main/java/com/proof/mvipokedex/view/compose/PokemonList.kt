package com.proof.mvipokedex.view.compose

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemsIndexed
import com.proof.mvipokedex.data.model.Result
import com.proof.mvipokedex.presentation.state.PokemonState
import com.proof.mvipokedex.presentation.viewModel.ViewModelPokemon
import kotlinx.coroutines.flow.Flow
import java.lang.Exception


@Composable
fun PokemonList(
    pokemons: Flow<PagingData<Result>>,
    vm: ViewModelPokemon,
    onButtonClick: (String) -> Unit
) {
    val resultItems: LazyPagingItems<Result> = pokemons.collectAsLazyPagingItems()
    LazyColumn {
        itemsIndexed(
            resultItems
        ) { index, item ->
            if (item != null) {
                PokemonItem(pokemon = item, vm, onButtonClick)
            }
        }
    }

    resultItems.apply {
        when (resultItems.loadState.append) {
            LoadState.Loading -> {
                LoadingScreen()
            }
            is LoadState.Error -> {
                vm.state.value = PokemonState.Error("Error")
            }
            else -> {
                try {
                    vm.state.value = PokemonState.PokemonList(vm.resultPokemon)
                } catch (e: Exception) {
                    PokemonState.Error(e.localizedMessage)
                }

            }
        }
    }

}