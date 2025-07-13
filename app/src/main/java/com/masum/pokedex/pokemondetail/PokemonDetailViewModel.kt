package com.masum.pokedex.pokemondetail

import androidx.lifecycle.ViewModel
import com.masum.pokedex.data.remote.responses.Pokemon
import com.masum.pokedex.repository.PokemonRepository
import com.masum.pokedex.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PokemonDetailViewModel @Inject constructor(
    private val repository: PokemonRepository
) : ViewModel() {
    suspend fun getPokemonInfo (pokemonName: String): Resource<Pokemon> {
        return repository.PokemonInfo(pokemonName)
    }
}