package com.proof.mvipokedex.presentation.viewModel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.proof.mvipokedex.domain.usecase.GetInfoPokemonUseCase
import javax.inject.Inject

class ViewModelFactoryPokemon @Inject constructor(
    private val app: Application,
    private val getInfoPokemonUseCase: GetInfoPokemonUseCase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(
            Application::class.java,
            GetInfoPokemonUseCase::class.java
        )
            .newInstance(
                app,
                getInfoPokemonUseCase

            )
    }
}