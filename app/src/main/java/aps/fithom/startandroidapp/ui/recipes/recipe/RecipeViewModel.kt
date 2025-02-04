package aps.fithom.startandroidapp.ui.recipes.recipe

import android.app.Application
import android.content.Context
import android.graphics.drawable.Drawable
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import aps.fithom.startandroidapp.data.local.getDrawableOrNullFromAssetsByPath
import aps.fithom.startandroidapp.data.remote.RecipesRepository
import aps.fithom.startandroidapp.domain.models.Recipe
import aps.fithom.startandroidapp.ui.recipes.recipe.RecipeFragment.Companion.PREFS_FAVORITE_SET
import aps.fithom.startandroidapp.ui.recipes.recipe.RecipeFragment.Companion.PREFS_NAME

class RecipeViewModel(private val application: Application) : AndroidViewModel(application) {

    private val _recipeStateLD = MutableLiveData<RecipeState>()
    val recipeStateLD: LiveData<RecipeState>
        get() = _recipeStateLD
    private val recipesRepository = RecipesRepository(application)

    private val prefs by lazy {
        application.baseContext.getSharedPreferences(
            PREFS_NAME,
            Context.MODE_PRIVATE
        )
    }

    init {
        Log.i("!!!", "RecipeViewModel init block")
        _recipeStateLD.value =
            _recipeStateLD.value?.copy(isInFavorite = true) ?: RecipeState(isInFavorite = true)
    }

    fun loadRecipe(recipeId: Int) {
        val recipe = recipesRepository.getRecipeById(recipeId)
        val isInFavorite = getFavoritesFromPrefs()?.contains(recipeId.toString()) == true
        _recipeStateLD.value =
            _recipeStateLD.value?.copy(
                recipe = recipe,
                isInFavorite = isInFavorite,
            )

    }

    private fun getFavoritesFromPrefs(): HashSet<String>? {
        return prefs.getStringSet(PREFS_FAVORITE_SET, null)?.toHashSet()
    }

    private fun saveFavorites(setOfIds: HashSet<String>) {
        prefs.edit().putStringSet(PREFS_FAVORITE_SET, setOfIds).apply()
    }

    fun onFavoritesClicked() {
        _recipeStateLD.value?.recipe?.id?.toString()?.let { recipeId ->
            val updatedSetOfFavorite = getFavoritesFromPrefs() ?: HashSet()
            if (updatedSetOfFavorite.contains(recipeId)) {
                updatedSetOfFavorite.remove(recipeId)
                _recipeStateLD.value = _recipeStateLD.value?.copy(isInFavorite = false)
            } else {
                updatedSetOfFavorite.add(recipeId)
                _recipeStateLD.value = _recipeStateLD.value?.copy(isInFavorite = true)
            }
            saveFavorites(updatedSetOfFavorite)
        }
    }

    fun updatePortionAmount(amount: Int) {
        _recipeStateLD.value = _recipeStateLD.value?.copy(portionAmount = amount)
    }


    data class RecipeState(
        val recipe: Recipe? = null,
        val isInFavorite: Boolean = false,
        val portionAmount: Int = 1,
    )

}