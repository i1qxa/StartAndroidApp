package aps.fithom.startandroidapp.ui.recipes.recipe

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import aps.fithom.startandroidapp.data.remote.RecipesRepository
import aps.fithom.startandroidapp.domain.models.Recipe
import kotlinx.coroutines.launch

class RecipeViewModel(private val application: Application) : AndroidViewModel(application) {

    private val _recipeStateLD = MutableLiveData<RecipeState>()
    val recipeStateLD: LiveData<RecipeState>
        get() = _recipeStateLD
    private val recipesRepository = RecipesRepository(application)

    init {
        Log.i("!!!", "RecipeViewModel init block")
        _recipeStateLD.value =
            _recipeStateLD.value?.copy(isInFavorite = true) ?: RecipeState(isInFavorite = true)
    }

    fun loadRecipe(recipeId: Int) {
        viewModelScope.launch {
            val recipe = recipesRepository.getRecipeById(recipeId).await()
            val isInFavorite = recipesRepository.getFavoriteStateByRecipeId(recipeId)
            _recipeStateLD.postValue(
                _recipeStateLD.value?.copy(
                    recipe = recipe,
                    isInFavorite = isInFavorite,
                )
            )
        }


    }

    fun onFavoritesClicked() {
        _recipeStateLD.value?.recipe?.id?.let { recipeId ->
            viewModelScope.launch {
                recipesRepository.changeRecipeFavoriteStateById(recipeId)
                _recipeStateLD.value?.let {
                    _recipeStateLD.postValue(it.copy(isInFavorite = recipesRepository.getFavoriteStateByRecipeId(recipeId)))
                }
            }
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