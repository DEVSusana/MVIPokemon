package com.proof.mvipokedex.data.repository.dataSource

import com.proof.mvipokedex.data.model.Pokemon
import com.proof.mvipokedex.data.model.PokemonList
import retrofit2.Response

interface RemoteDataSource {
    suspend fun getPokeList(limit: Int, offset: Int): Response<PokemonList>
    suspend fun getPokeInfo(name: String): Response<Pokemon>
}