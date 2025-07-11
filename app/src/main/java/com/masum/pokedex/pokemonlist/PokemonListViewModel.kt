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
import kotlinx.coroutines.launch

@HiltViewModel
class PokemonListViewModel @Inject constructor(
    private val repository : PokemonRepository
) : ViewModel() {

    private val currPage = 0
    var pokemonList = mutableStateOf<List<PokedexListEntry>>(listOf())
    val loadError = mutableStateOf("")
    val isLoading = mutableStateOf(false)
    val endReached = mutableStateOf(false)

    fun loadPokemonPaginated() {
        viewModelScope.launch {
            val result = repository.PokemonList(PAGE_SIZE, currPage * PAGE_SIZE)
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