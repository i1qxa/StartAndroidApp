package aps.fithom.startandroidapp.ui.recipes.favorites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.switchMap
import aps.fithom.startandroidapp.data.remote.RecipesRepository
import aps.fithom.startandroidapp.domain.models.Recipe
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(recipesRepository: RecipesRepository) : ViewModel() {

    val favoriteStateLD = recipesRepository.favoriteRecipes.switchMap { favoriteRecipes ->
        liveData { emit(FavoritesState(favoriteRecipes.await())) }
    }


    data class FavoritesState(
        val recipesList: List<Recipe>? = emptyList()
    )

}