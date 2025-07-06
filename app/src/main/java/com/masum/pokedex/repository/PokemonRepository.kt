package com.masum.pokedex.repository

import androidx.compose.ui.geometry.Offset
import com.masum.pokedex.data.remote.responses.PokeAPI
import com.masum.pokedex.data.remote.responses.PokemonList
import dagger.hilt.android.scopes.ActivityScoped
import javax.inject.Inject

@ActivityScoped
class PokemonRepository @Inject constructor(
    private val api : PokeAPI
) {
    suspend fun PokemonList(limit: Int, offset: Int) : PokemonList {
        val response = try {
            api.getPokemonList(limit, offset)
        } catch (e: Exception) {

        }
    }
}