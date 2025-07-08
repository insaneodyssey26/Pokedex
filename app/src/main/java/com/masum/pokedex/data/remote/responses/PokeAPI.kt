package com.masum.pokedex.data.remote.responses

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PokeAPI {

    @GET("pokemon")
    suspend fun getPokemonList (
        @Query("limit") limit : Int,
        @Query("offset") offset : Int
    ) : PokemonList

    @GET("pokemon/{name}")
    suspend fun  getPokemonDetails(
        @Path ("name") name: String
    ) : Pokemon
}