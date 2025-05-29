package aps.fithom.startandroidapp.ui.recipes.recipe

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import aps.fithom.startandroidapp.data.remote.RecipesRepository
import aps.fithom.startandroidapp.domain.models.Recipe
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecipeViewModel @Inject constructor(private val recipesRepository: RecipesRepository) : ViewModel() {

    private val selectedRecipeLD = MutableLiveData<Recipe>()
    private val _recipeStateLD = MediatorLiveData<RecipeState>().apply {
        addSource(selectedRecipeLD) { recipe ->
            this.value?.let {
                this.value = it.copy(recipe = recipe)
            }
        }
        addSource(recipesRepository.isSelectedRecipeInFavoriteLD) { isInFavorite ->
            this.value?.let {
                this.value = it.copy(isInFavorite = isInFavorite)
            }
        }
    }
    val recipeStateLD: LiveData<RecipeState>
        get() = _recipeStateLD

    init {
        Log.i("!!!", "RecipeViewModel init block")
        _recipeStateLD.value =
            _recipeStateLD.value?.copy(isInFavorite = true) ?: RecipeState(isInFavorite = true)
    }

    fun loadRecipe(recipeId: Int) {
        recipesRepository.selectRecipeId(recipeId)
        viewModelScope.launch {
            selectedRecipeLD.postValue(recipesRepository.getRecipeById(recipeId))
        }
    }

    fun onFavoritesClicked() {
        _recipeStateLD.value?.recipe?.id?.let { recipeId ->
            viewModelScope.launch {
                recipesRepository.changeRecipeFavoriteStateById(recipeId)
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