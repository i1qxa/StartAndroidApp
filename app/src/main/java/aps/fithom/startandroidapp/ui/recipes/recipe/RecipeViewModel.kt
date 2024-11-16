package aps.fithom.startandroidapp.ui.recipes.recipe

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import aps.fithom.startandroidapp.domain.models.Recipe

class RecipeViewModel : ViewModel() {

    private val _recipeStateLD = MutableLiveData<RecipeState>()
    val recipeStateLD: LiveData<RecipeState>
        get() = _recipeStateLD

    init {
        Log.i("!!!", "RecipeViewModel init block")
        _recipeStateLD.value = RecipeState(isInFavorite = true)
    }


    data class RecipeState(
        val recipe: Recipe? = null,
        val isInFavorite: Boolean = false,
        val portionAmount: Int = 1,
    )

}