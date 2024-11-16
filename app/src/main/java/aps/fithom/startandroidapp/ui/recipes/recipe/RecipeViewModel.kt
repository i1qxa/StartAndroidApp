package aps.fithom.startandroidapp.ui.recipes.recipe

import androidx.lifecycle.ViewModel
import aps.fithom.startandroidapp.data.local.Recipe

class RecipeViewModel : ViewModel() {


    data class RecipeState(
        val recipe: Recipe? = null,
        var isInFavorite: Boolean = false
    )

}