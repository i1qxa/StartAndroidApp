package aps.fithom.startandroidapp.ui.recipes.recipes_list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import aps.fithom.startandroidapp.data.remote.RecipesRepository
import aps.fithom.startandroidapp.domain.models.Category
import aps.fithom.startandroidapp.domain.models.Recipe
import kotlinx.coroutines.launch

class RecipesListViewModel(private val recipesRepository: RecipesRepository) : ViewModel() {

    private val _recipesListStateLD = recipesRepository.categoryWithRecipesLD.switchMap {
        MutableLiveData(
            RecipesListState(
                it.category,
                it.recipes.map { recipeDB -> recipeDB.toRecipe() })
        )
    }
    val recipesListStateLD: LiveData<RecipesListState>
        get() = _recipesListStateLD

    data class RecipesListState(
        val category: Category? = null,
        val recipesList: List<Recipe>? = emptyList()
    )

    fun loadCategoryAndUpdateRecipesList(category: Category) {
        viewModelScope.launch {
            recipesRepository.selectCategoryById(category.id)
        }
    }

}