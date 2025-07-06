package com.masum.pokedex.repository

import androidx.compose.ui.geometry.Offset
import com.masum.pokedex.data.remote.responses.PokeAPI
import com.masum.pokedex.data.remote.responses.Pokemon
import com.masum.pokedex.data.remote.responses.PokemonList
import com.masum.pokedex.util.Resource
import dagger.hilt.android.scopes.ActivityScoped
import javax.inject.Inject

@ActivityScoped
class PokemonRepository @Inject constructor(
    private val api : PokeAPI
) {
    suspend fun PokemonList(limit: Int, offset: Int) : Resource<PokemonList> {
        val response = try {
            api.getPokemonList(limit, offset)
        } catch (e: Exception) {
            return Resource.Error ("Unknown error occurred" )
        }
        return Resource.Success(response)
    }

    suspend fun PokemonInfo(pokemonName: String) : Resource<Pokemon> {
        val response = try {
            api.getPokemonDetails(pokemonName)
        } catch (e: Exception) {
            return Resource.Error ("Unknown error occurred" )
        }
        return Resource.Success(response)
    }
}