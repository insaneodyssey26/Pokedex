package com.masum.pokedex.data.remote.responses

import com.google.gson.internal.NumberLimits
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

@GET("pokemon")
interface PokeAPI {
    suspend fun getPokemonList (
        @Query("limit") limit : Int,
        @Query("offset") offset : Int
    ) : PokemonList

    @GET("pokemon/{name}")
    suspend fun  getPokemonDetails(
        @Path ("name") name: String
    ) : Pokemon
}