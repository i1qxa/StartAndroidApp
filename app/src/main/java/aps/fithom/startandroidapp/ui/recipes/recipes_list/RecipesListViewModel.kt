package aps.fithom.startandroidapp.ui.recipes.recipes_list

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.viewModelScope
import aps.fithom.startandroidapp.data.remote.RecipesRepository
import aps.fithom.startandroidapp.domain.models.Category
import aps.fithom.startandroidapp.domain.models.Recipe
import kotlinx.coroutines.launch

class RecipesListViewModel(private val application: Application) : AndroidViewModel(application) {

    val recipesRepository = RecipesRepository(application)

    //    private val _recipesListStateLD = recipesRepository.categoryWithRecipesLD.switchMap {
//        MutableLiveData(
//            RecipesListState(
//                it.category,
//                it.recipes.map { recipeDB -> recipeDB.toRecipe() })
//        )
//    }
    private val _recipesListStateLD = MediatorLiveData<RecipesListState>()
//        .apply {
//        addSource(recipesRepository.selectedCategoryLD){ categoryDB ->
//            this.value?.let {
//                this.value = it.copy(category = categoryDB)
//            }
//        }
//        addSource(recipesRepository.recipesInSelectedCategory){ listRecipeDB ->
//            this.value?.let {
//                this.value = it.copy(recipesList = listRecipeDB.map { it.toRecipe() })
//            }
//        }
//    }

    val recipesListStateLD: LiveData<RecipesListState>
        get() = _recipesListStateLD

    data class RecipesListState(
        val category: Category? = null,
        val recipesList: List<Recipe>? = emptyList()
    )

    fun loadCategoryAndUpdateRecipesList(category: Category) {
        viewModelScope.launch {
            recipesRepository.fetchRecipesByCategoryId(category.id)
        }

    }

    init {
        _recipesListStateLD.addSource(recipesRepository.selectedCategoryLD){ categoryDB ->
            _recipesListStateLD.value?.let {
                _recipesListStateLD.value = it.copy(category = categoryDB)
            }
        }
        _recipesListStateLD.addSource(recipesRepository.recipesInSelectedCategory){ listRecipeDB ->
            _recipesListStateLD.value?.let {
                _recipesListStateLD.value = it.copy(recipesList = listRecipeDB.map { it.toRecipe() })
            }
        }
    }

}