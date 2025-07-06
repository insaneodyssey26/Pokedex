package com.masum.pokedex.di

import com.masum.pokedex.data.remote.responses.PokeAPI
import com.masum.pokedex.repository.PokemonRepository
import com.masum.pokedex.util.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    fun providePokemonRepository(
        api: PokeAPI
    ) = PokemonRepository(api)

    @Singleton
    @Provides
    fun pokeApi() : PokeAPI {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
            .create(PokeAPI::class.java)
    }
}