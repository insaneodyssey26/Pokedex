package com.masum.pokedex.pokemonlist

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.masum.pokedex.repository.PokemonRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import androidx.palette.graphics.Palette
import com.masum.pokedex.data.models.PokedexListEntry
import com.masum.pokedex.util.Constants.PAGE_SIZE
import com.masum.pokedex.util.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.Dispatcher
import retrofit2.http.Query
import java.util.Locale

@HiltViewModel
class PokemonListViewModel @Inject constructor(
    private val repository : PokemonRepository
) : ViewModel() {

    private var currPage = 0
    var pokemonList = mutableStateOf<List<PokedexListEntry>>(listOf())
    val loadError = mutableStateOf("")
    val isLoading = mutableStateOf(false)
    val endReached = mutableStateOf(false)

    private var cachedPokemonList = listOf<PokedexListEntry>()
    private var isSearchStarting = true
    var isSearching = mutableStateOf(false)

    init {
        loadPokemonPaginated()
    }

    fun searchPokemonList(query: String) {
        if (query.isEmpty()) {
            pokemonList.value = cachedPokemonList
            isSearching.value = false
            isSearchStarting = true
            return
        }
        if (isSearchStarting) {
            cachedPokemonList = pokemonList.value
            isSearchStarting = false
        }
        val results = cachedPokemonList.filter {
            it.pokemonName.contains(query.trim(), ignoreCase = true) ||
                    it.number.toString() == query.trim()
        }
        pokemonList.value = results
        isSearching.value = true
    }

    fun loadPokemonPaginated() {
        if (isSearching.value) return // Prevent pagination while searching
        viewModelScope.launch {
            val result = repository.PokemonList(PAGE_SIZE, currPage * PAGE_SIZE)
            when(result) {
                is Resource.Success -> {
                    endReached.value = currPage * PAGE_SIZE >= result.data!!.count
                    val PokedexListEntry = result.data.results.mapIndexed { index, entry ->
                        val number = if(entry.url.endsWith("/")) {
                            entry.url.dropLast(1).takeLastWhile { it.isDigit() }
                        } else {
                            entry.url.takeLastWhile { it.isDigit() }
                        }
                        val url = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/${number}.png"
                        PokedexListEntry(entry.name.replaceFirstChar { if (it.isLowerCase()) it.titlecase(
                            Locale.ROOT
                        ) else it.toString() }, url, number.toInt())
                    }
                    currPage++
                    loadError.value = ""
                    isLoading.value = false
                    pokemonList.value += PokedexListEntry
                }
                is Resource.Error -> {
                   loadError.value = result.message!!
                    isLoading.value = false
                }
                is Resource.Loading -> {
                    // Optionally handle loading state
                }
            }
        }
    }
    fun DominantColor (drawable: Drawable, onFinish: (Color) -> Unit) {
        val bmap = (drawable as BitmapDrawable).bitmap.copy(Bitmap.Config.ARGB_8888, true)

        Palette.from(bmap).generate { palette ->
            palette?.dominantSwatch?.rgb?.let { colorValue ->
                onFinish(Color(colorValue))
            }
        }
    }
}