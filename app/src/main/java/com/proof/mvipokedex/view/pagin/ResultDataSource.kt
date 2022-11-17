package com.proof.mvipokedex.view.pagin

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.proof.mvipokedex.data.model.Result
import com.proof.mvipokedex.presentation.di.NetModule
import retrofit2.HttpException
import java.io.IOException

class ResultDataSource : PagingSource<Int, Result>() {
    override fun getRefreshKey(state: PagingState<Int, Result>): Int? {
        return state.anchorPosition

    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Result> {
        return try {
            val nextPage = params.key ?: 1
            val sizeList = 10
            val pokemonList = NetModule.provideApiService(NetModule.provideRetrofit())
                .getPokemonList(sizeList, nextPage + sizeList)
            LoadResult.Page(
                data = pokemonList.body()!!.results,
                prevKey = null,
                nextKey = calcNextIndex(pokemonList.body()!!.next)
            )
        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
        }
    }

    private fun calcNextIndex(pokemon: String): Int {
        val offsetIndex = pokemon.indexOf("offset=", 0)
        val nextPage = pokemon.subSequence(offsetIndex + 7, offsetIndex + 9)
        return nextPage.toString().toInt()
    }
}