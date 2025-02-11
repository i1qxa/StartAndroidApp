package aps.fithom.startandroidapp.ui.recipes.recipes_list

import android.app.Application
import android.graphics.drawable.Drawable
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import aps.fithom.startandroidapp.data.local.getDrawableOrNullFromAssetsByPath
import aps.fithom.startandroidapp.data.remote.RecipesRepository
import aps.fithom.startandroidapp.domain.models.Category
import aps.fithom.startandroidapp.domain.models.Recipe
import kotlinx.coroutines.launch

class RecipesListViewModel(private val application: Application) : AndroidViewModel(application) {

    private val _recipesListStateLD = MutableLiveData<RecipesListState>(RecipesListState())
    val recipesListStateLD: LiveData<RecipesListState>
        get() = _recipesListStateLD
    private val recipesRepository = RecipesRepository()

    data class RecipesListState(
        val category: Category? = null,
        val recipesList: List<Recipe>? = emptyList(),
        val categoryImg: Drawable? = null
    )

    fun loadCategoryAndUpdateRecipesList(category: Category) {
        _recipesListStateLD.value =
            _recipesListStateLD.value?.copy(category = category)
        viewModelScope.launch {
            _recipesListStateLD.value?.category?.id?.let { categoryId ->
                val recipes = recipesRepository.getRecipesByCategoryId(categoryId).await()
                _recipesListStateLD.postValue(
                    _recipesListStateLD.value?.copy(recipesList = recipes)
                )
            }
        }

        _recipesListStateLD.value?.category?.imageUrl?.let { imgPath ->
            _recipesListStateLD.value = _recipesListStateLD.value?.copy(
                categoryImg = application.getDrawableOrNullFromAssetsByPath(imgPath)
            )
        }
    }

}