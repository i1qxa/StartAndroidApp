package aps.fithom.startandroidapp.ui.recipes.favorites

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.switchMap
import aps.fithom.startandroidapp.data.remote.RecipesRepository
import aps.fithom.startandroidapp.domain.models.Recipe

class FavoritesViewModel(private val application: Application) : AndroidViewModel(application) {

    private val recipesRepository = RecipesRepository(application)
    val favoriteStateLD = recipesRepository.favoriteRecipes.switchMap { favoriteRecipes ->
        liveData { emit(FavoritesState(favoriteRecipes.await())) }
    }


    data class FavoritesState(
        val recipesList: List<Recipe>? = emptyList()
    )

}