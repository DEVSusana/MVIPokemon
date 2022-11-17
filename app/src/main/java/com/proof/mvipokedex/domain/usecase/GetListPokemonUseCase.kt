package com.proof.mvipokedex.domain.usecase

import com.proof.mvipokedex.data.model.PokemonList
import com.proof.mvipokedex.data.utils.Resource
import com.proof.mvipokedex.domain.repository.Repository

class GetListPokemonUseCase (private val repository: Repository){
    suspend fun execute(limit: Int, offset: Int): Resource<PokemonList>{
        return repository.getPokeList(limit, offset)
    }
}