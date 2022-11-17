package com.proof.mvipokedex.view.compose

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.proof.mvipokedex.data.model.Result
import com.proof.mvipokedex.presentation.viewModel.ViewModelPokemon
import com.proof.mvipokedex.view.ui.theme.TypeIce


@OptIn(ExperimentalCoilApi::class, ExperimentalMaterialApi::class)
@Composable
fun PokemonItem(pokemon: Result, vm: ViewModelPokemon, onButtonClick: () -> Unit) {
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
                .fillMaxWidth()
                .clickable (
                    onClick = onButtonClick
                ),
            verticalAlignment = Alignment.CenterVertically
        ) {
            vm.pokemonName.value = pokemon.name
            val number = if (pokemon.url.endsWith("/")) {
                pokemon.url.dropLast(1).takeLastWhile { it.isDigit() }
            } else {
                pokemon.url.takeLastWhile { it.isDigit() }
            }
            val urlImage =
                "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/${number}.png"

            val painter = rememberImagePainter(data = urlImage)
            Image(
                painter = painter,
                contentDescription = null,
                modifier = Modifier
                    .padding(4.dp)
                    .size(100.dp)
                    .clip(RoundedCornerShape(corner = CornerSize(10.dp)))
                    .background(color = Color.LightGray),
                contentScale = ContentScale.FillHeight
            )

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
                Text(
                    text = pokemon.name,
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.h5,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}