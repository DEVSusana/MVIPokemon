package com.proof.mvipokedex.presentation.viewModel

import android.app.Application
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.proof.mvipokedex.data.model.Pokemon
import com.proof.mvipokedex.domain.usecase.GetInfoPokemonUseCase
import com.proof.mvipokedex.presentation.intent.PokemonIntent
import com.proof.mvipokedex.presentation.state.PokemonState
import com.proof.mvipokedex.view.pagin.ResultDataSource
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch
import com.proof.mvipokedex.data.model.Result
import com.proof.mvipokedex.data.utils.Resource
import java.lang.Exception

class ViewModelPokemon(
    private val app: Application,
    private val getInfoPokemonUseCase: GetInfoPokemonUseCase
) : AndroidViewModel(app) {
    val pokemonIntent = Channel<PokemonIntent>(Channel.UNLIMITED)
    var state = mutableStateOf<PokemonState>(PokemonState.Loading)
        private set
    var pokemonName = MutableLiveData<String>()

    init {
        handleIntent()
    }

    private fun handleIntent() {
        viewModelScope.launch {
            pokemonIntent.consumeAsFlow().collect { collector ->
                when (collector) {
                    is PokemonIntent.FetchPokemonList -> fetchPokemon()
                    is PokemonIntent.CheckPokemonInfo -> checkInfoPokemon(pokemonName.value.toString())
                }

            }
        }
    }

    val resultPokemon: Flow<PagingData<Result>> = Pager(PagingConfig(pageSize = 10)) {
        ResultDataSource()
    }.flow.cachedIn(viewModelScope)

    private fun fetchPokemon() {
        viewModelScope.launch {
            state.value = PokemonState.Loading
            try {
                state.value = PokemonState.PokemonList(resultPokemon)
            } catch (e: Exception) {
                PokemonState.Error(e.localizedMessage)
            }

        }
    }

    private val _getDetail: MutableLiveData<Resource<Pokemon>> = MutableLiveData()
    val getDetail get() = _getDetail

    private fun checkInfoPokemon(name: String) {
        viewModelScope.launch {
            getDetail.postValue(Resource.Loading())
            state.value = PokemonState.Loading
            val pokeInfo = getInfoPokemonUseCase.execute(name)
            getDetail.postValue(pokeInfo)
            state.value = try {
                PokemonState.PokemonInfo(getDetail.value)
            } catch (e: Exception) {
                PokemonState.Error(e.localizedMessage)
            }

        }
    }

}