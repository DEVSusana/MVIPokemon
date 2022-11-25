package com.proof.mvipokedex.view.compose

import android.util.Log
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.proof.mvipokedex.data.model.Pokemon
import com.proof.mvipokedex.data.utils.Resource
import com.proof.mvipokedex.presentation.state.PokemonState
import com.proof.mvipokedex.presentation.viewModel.ViewModelPokemon
import com.proof.mvipokedex.view.ui.theme.TypeIce


@Composable
fun PokemonDetail( viewModelPokemon: ViewModelPokemon, navController: NavController) {
    BackHandler() {
        navController.popBackStack()
    }

    when (viewModelPokemon.getDetail.value) {
        is Resource.Error -> {
            (viewModelPokemon.getDetail.value as Resource.Error<Pokemon>).message?.let {
                Toast.makeText(LocalContext.current, "An error occurred : $it", Toast.LENGTH_LONG)
                    .show()
                Log.i("ERROR", it)
            }
        }
        is Resource.Loading -> {
            LoadingScreen()
        }

        else -> {
            val detailPokemon by remember{
                mutableStateOf(viewModelPokemon.getDetail.value)
            }
            viewModelPokemon.state.value = PokemonState.PokemonInfo(viewModelPokemon.getDetail.value)
            Card(
                modifier = Modifier
                    .padding(10.dp)
                    .fillMaxWidth()
                    .height(100.dp),
                elevation = 10.dp,
                shape = RoundedCornerShape(corner = CornerSize(10.dp))
            ) {
                Row(
                    modifier = Modifier
                        .padding(5.dp)
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                ) {

                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(start = 4.dp)
                            .background(
                                color = TypeIce,
                                shape = RoundedCornerShape(10.dp)
                            ),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        detailPokemon?.data?.let {
                            Text(
                                text = it.abilities[0].ability.name,
                                fontWeight = FontWeight.Bold,
                                style = MaterialTheme.typography.h5,
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }
            }
        }
    }
}