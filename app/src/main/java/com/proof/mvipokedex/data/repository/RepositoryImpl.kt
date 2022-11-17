package com.proof.mvipokedex.data.repository

import com.proof.mvipokedex.data.model.Pokemon
import com.proof.mvipokedex.data.model.PokemonList
import com.proof.mvipokedex.data.repository.dataSource.RemoteDataSource
import com.proof.mvipokedex.data.utils.Resource
import com.proof.mvipokedex.domain.repository.Repository
import retrofit2.Response
import javax.inject.Inject

class RepositoryImpl @Inject constructor(private val remoteDataSource: RemoteDataSource) :
    Repository {
    override suspend fun getPokeList(limit: Int, offset: Int): Resource<PokemonList> {
        val response = try {
            remoteDataSource.getPokeList(limit, offset)
        } catch(e: Exception) {
            return Resource.Error("An unknown error occured.")
        }
        return responseToResourceList(
            response
        )
    }

    override suspend fun getPokeInfo(name: String): Resource<Pokemon> {
        val response = try {
            remoteDataSource.getPokeInfo(name)
        } catch(e: Exception) {
            return Resource.Error("An unknown error occured.")
        }
        return responseToResourceInfo(response)
    }


    private fun responseToResourceList(response: Response<PokemonList>): Resource<PokemonList> {
        if (response.isSuccessful) {
            response.body()?.let { result ->
                return Resource.Success(result)
            }
        }
        return Resource.Error(response.message())
    }

    private fun responseToResourceInfo(response: Response<Pokemon>): Resource<Pokemon> {
        if (response.isSuccessful) {
            response.body()?.let { result ->
                return Resource.Success(result)
            }
        }
        return Resource.Error(response.message())
    }

}