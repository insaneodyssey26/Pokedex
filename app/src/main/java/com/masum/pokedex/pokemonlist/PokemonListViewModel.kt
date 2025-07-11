package com.masum.pokedex.pokemonlist

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import com.masum.pokedex.repository.PokemonRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import androidx.palette.graphics.Palette
import com.masum.pokedex.data.models.PokedexListEntry

@HiltViewModel
class PokemonListViewModel @Inject constructor(
    private val repository : PokemonRepository
) : ViewModel() {

    private val currPage = 0
    var pokemonList = mutableStateOf<List<PokedexListEntry>>(listOf())
    fun DominantColor (drawable: Drawable, onFinish: (Color) -> Unit) {
        val bmap = (drawable as BitmapDrawable).bitmap.copy(Bitmap.Config.ARGB_8888, true)

        Palette.from(bmap).generate { palette ->
            palette?.dominantSwatch?.rgb?.let { colorValue ->
                onFinish(Color(colorValue))
            }
        }
    }
}