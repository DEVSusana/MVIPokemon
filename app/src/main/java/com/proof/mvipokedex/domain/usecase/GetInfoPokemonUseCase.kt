package com.proof.mvipokedex.domain.usecase

import com.proof.mvipokedex.data.model.Pokemon
import com.proof.mvipokedex.data.utils.Resource
import com.proof.mvipokedex.domain.repository.Repository

class GetInfoPokemonUseCase (private val repository: Repository){
    suspend fun execute(name: String): Resource<Pokemon>{
        return repository.getPokeInfo(name)
    }
}