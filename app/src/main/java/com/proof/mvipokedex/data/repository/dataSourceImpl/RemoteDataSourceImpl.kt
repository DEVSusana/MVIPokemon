package com.proof.mvipokedex.data.repository.dataSourceImpl

import com.proof.mvipokedex.data.api.ApiService
import com.proof.mvipokedex.data.model.Pokemon
import com.proof.mvipokedex.data.model.PokemonList
import com.proof.mvipokedex.data.repository.dataSource.RemoteDataSource
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RemoteDataSourceImpl @Inject constructor(private val apiService: ApiService) :
    RemoteDataSource {
    override suspend fun getPokeList(limit: Int, offset: Int): Response<PokemonList> {
        return apiService.getPokemonList(limit, offset)
    }

    override suspend fun getPokeInfo(name: String): Response<Pokemon> {
        return apiService.getPokemonInfo(name)
    }
}