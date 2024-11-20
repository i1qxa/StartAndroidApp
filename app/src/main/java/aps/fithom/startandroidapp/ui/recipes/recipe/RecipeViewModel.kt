package aps.fithom.startandroidapp.ui.recipes.recipe

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import aps.fithom.startandroidapp.data.local.STUB
import aps.fithom.startandroidapp.domain.models.Recipe
import aps.fithom.startandroidapp.ui.recipes.recipe.RecipeFragment.Companion.PREFS_FAVORITE_SET
import aps.fithom.startandroidapp.ui.recipes.recipe.RecipeFragment.Companion.PREFS_NAME

class RecipeViewModel(application: Application) : AndroidViewModel(application) {

    private val _recipeStateLD = MutableLiveData<RecipeState>()
    val recipeStateLD: LiveData<RecipeState>
        get() = _recipeStateLD

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
        //TODO load from network
        val recipe = STUB.getRecipeById(recipeId)
        val isInFavorite = getFavoritesFromPrefs()?.contains(recipeId.toString()) ?: false
        _recipeStateLD.value =
            _recipeStateLD.value?.copy(recipe = recipe, isInFavorite = isInFavorite)

    }

    private fun getFavoritesFromPrefs(): HashSet<String>? {
        return prefs.getStringSet(PREFS_FAVORITE_SET, null)?.toHashSet()
    }

    private fun saveFavorites(setOfIds: HashSet<String>) {
        prefs.edit().putStringSet(PREFS_FAVORITE_SET, setOfIds).apply()
    }

    fun onFavoritesClicked() {
        _recipeStateLD.value?.recipe?.id?.toString()?.let { recipeId ->
            val updatedSetOfFavorite = HashSet<String>()
            getFavoritesFromPrefs()?.let { savedSetOfFavorite ->
                updatedSetOfFavorite.addAll(savedSetOfFavorite)
            }
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


    data class RecipeState(
        val recipe: Recipe? = null,
        val isInFavorite: Boolean = false,
        val portionAmount: Int = 1,
    )

}