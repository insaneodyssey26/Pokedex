package com.masum.pokedex.repository

import com.masum.pokedex.data.remote.responses.PokeAPI
import com.masum.pokedex.data.remote.responses.Pokemon
import com.masum.pokedex.data.remote.responses.PokemonList
import com.masum.pokedex.util.Resource
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PokemonRepository @Inject constructor(
    private val api : PokeAPI
) {
    suspend fun PokemonList(limit: Int, offset: Int) : Resource<PokemonList> {
        val response = try {
            api.getPokemonList(limit, offset)
        } catch (_: Exception) {
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