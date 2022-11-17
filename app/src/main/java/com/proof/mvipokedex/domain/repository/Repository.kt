package com.proof.mvipokedex.domain.repository

import com.proof.mvipokedex.data.model.Pokemon
import com.proof.mvipokedex.data.model.PokemonList
import com.proof.mvipokedex.data.utils.Resource

interface Repository {
    suspend fun getPokeList(limit: Int, offset: Int): Resource<PokemonList>
    suspend fun getPokeInfo(name: String): Resource<Pokemon>
}