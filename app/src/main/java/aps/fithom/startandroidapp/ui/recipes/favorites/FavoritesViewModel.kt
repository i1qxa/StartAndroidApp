package aps.fithom.startandroidapp.ui.recipes.favorites

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import aps.fithom.startandroidapp.data.remote.RecipesRepository
import aps.fithom.startandroidapp.domain.models.Recipe
import aps.fithom.startandroidapp.ui.recipes.recipe.RecipeFragment.Companion.PREFS_FAVORITE_SET
import aps.fithom.startandroidapp.ui.recipes.recipe.RecipeFragment.Companion.PREFS_NAME
import kotlinx.coroutines.launch

class FavoritesViewModel(private val application: Application) : AndroidViewModel(application) {

//    private val prefs by lazy {
//        application.getSharedPreferences(
//            PREFS_NAME,
//            Context.MODE_PRIVATE
//        )
//    }

    private val recipesRepository = RecipesRepository(application)
    private val _favoritesStateLD = recipesRepository.favoriteRecipes.switchMap { MutableLiveData(FavoritesState(it)) }
    val favoriteStateLD: LiveData<FavoritesState>
        get() = _favoritesStateLD

//    private fun getFavoritesFromPrefs(): Set<Int>? {
//        return prefs.getStringSet(PREFS_FAVORITE_SET, null)?.map {
//            it.toInt()
//        }?.toSet()
//    }
//
//    fun updateFavoriteState() {
//        viewModelScope.launch {
//            getFavoritesFromPrefs()?.let { setIds ->
//                val favoriteRecipes = recipesRepository.getRecipesByIds(setIds).await()
//                _favoritesStateLD.postValue(
//                    _favoritesStateLD.value?.copy(recipesList = favoriteRecipes)
//                )
//            }
//        }
//
//    }

    data class FavoritesState(
        val recipesList: List<Recipe>? = emptyList()
    )

}