package com.proof.mvipokedex.presentation.intent

sealed class PokemonIntent {
    object FetchPokemonList: PokemonIntent()
    object CheckPokemonInfo: PokemonIntent()
}