package com.proof.mvipokedex.presentation.di

import com.proof.mvipokedex.domain.repository.Repository
import com.proof.mvipokedex.domain.usecase.GetInfoPokemonUseCase
import com.proof.mvipokedex.domain.usecase.GetListPokemonUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {
    @Provides
    @Singleton
    fun provideGetListPokemonUseCase(repository: Repository): GetListPokemonUseCase {
        return GetListPokemonUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideGetInfoPokemonUseCase(repository: Repository): GetInfoPokemonUseCase {
        return GetInfoPokemonUseCase(repository)
    }


}