package com.proof.mvipokedex.presentation.state

import androidx.paging.PagingData
import com.proof.mvipokedex.data.model.Pokemon
import com.proof.mvipokedex.data.model.Result
import com.proof.mvipokedex.data.utils.Resource
import kotlinx.coroutines.flow.Flow

sealed class PokemonState {
    object Loading : PokemonState()
    data class PokemonList(val pokemons: Flow<PagingData<Result>>): PokemonState()
    data class PokemonInfo(val pokemonInfo: Resource<Pokemon>?): PokemonState()
    data class Error(val error: String?): PokemonState()
}