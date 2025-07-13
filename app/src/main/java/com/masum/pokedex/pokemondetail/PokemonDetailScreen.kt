package com.masum.pokedex.pokemondetail

import androidx.compose.runtime.Composable
import androidx.compose.runtime.produceState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.masum.pokedex.data.remote.responses.Pokemon
import com.masum.pokedex.util.Resource

@Composable
fun PokemonDetailScreen(
    dominantColor: Color,
    pokemonName: String,
    navController: NavController,
    topPadding: Dp = 20.dp,
    pokemonImageSize: Dp = 200.dp,
    viewModel: PokemonDetailViewModel = hiltViewModel()
) {
    val PokemonInfo = produceState<Resource<Pokemon>>(initialValue = Resource.Loading<Pokemon>()) {
        value = viewModel.getPokemonInfo(pokemonName)
    }

}