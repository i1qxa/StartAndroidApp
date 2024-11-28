package aps.fithom.startandroidapp.ui.recipes.favorites

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import aps.fithom.startandroidapp.data.local.STUB
import aps.fithom.startandroidapp.domain.models.Recipe
import aps.fithom.startandroidapp.ui.recipes.recipe.RecipeFragment.Companion.PREFS_FAVORITE_SET
import aps.fithom.startandroidapp.ui.recipes.recipe.RecipeFragment.Companion.PREFS_NAME

class FavoritesViewModel(private val application: Application) : AndroidViewModel(application) {

    private val prefs by lazy {
        application.getSharedPreferences(
            PREFS_NAME,
            Context.MODE_PRIVATE
        )
    }
    private val _favoritesStateLD = MutableLiveData(FavoritesState())
    val favoriteStateLD: LiveData<FavoritesState>
        get() = _favoritesStateLD

    private fun getFavoritesFromPrefs(): Set<Int>? {
        return prefs.getStringSet(PREFS_FAVORITE_SET, null)?.map {
            it.toInt()
        }?.toSet()
    }

    fun updateFavoriteState() {
        getFavoritesFromPrefs()?.let { setIds ->
            _favoritesStateLD.value =
                _favoritesStateLD.value?.copy(recipesList = STUB.getRecipesByIds(setIds))
        }
    }

    data class FavoritesState(
        val recipesList: List<Recipe> = emptyList()
    )

}