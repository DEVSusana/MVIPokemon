package com.proof.mvipokedex.presentation.di

import android.app.Application
import androidx.lifecycle.ViewModelProvider
import com.proof.mvipokedex.domain.usecase.GetInfoPokemonUseCase
import com.proof.mvipokedex.presentation.viewModel.ViewModelFactoryPokemon
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object FactoryModule {
    @Provides
    @Singleton
    fun provideViewModelFactory(
        application: Application,
        getInfoPokemonUseCase: GetInfoPokemonUseCase

        ): ViewModelProvider.Factory {
        return ViewModelFactoryPokemon(
            application,
            getInfoPokemonUseCase
        )
    }

}