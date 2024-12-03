package aps.fithom.startandroidapp.ui.recipes.recipes_list

import android.app.Application
import android.graphics.drawable.Drawable
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import aps.fithom.startandroidapp.data.local.STUB
import aps.fithom.startandroidapp.data.local.getDrawableOrNullFromAssetsByPath
import aps.fithom.startandroidapp.domain.models.Category
import aps.fithom.startandroidapp.domain.models.Recipe

class RecipesListViewModel(private val application: Application) : AndroidViewModel(application) {

    private val _recipesListStateLD = MutableLiveData<RecipesListState>(RecipesListState())
    val recipesListStateLD: LiveData<RecipesListState>
        get() = _recipesListStateLD

    data class RecipesListState(
        val category: Category? = null,
        val recipesList: List<Recipe> = emptyList(),
        val categoryImg: Drawable? = null
    )

    fun loadCategoryAndUpdateRecipesList(category: Category) {
        _recipesListStateLD.value =
            _recipesListStateLD.value?.copy(category = category)
        _recipesListStateLD.value?.category?.id?.let {
            _recipesListStateLD.value =
                _recipesListStateLD.value?.copy(recipesList = STUB.getRecipesByCategoryId(it))
        }
        _recipesListStateLD.value?.category?.imageUrl?.let { imgPath ->
            _recipesListStateLD.value = _recipesListStateLD.value?.copy(
                categoryImg = application.getDrawableOrNullFromAssetsByPath(imgPath)
            )
        }
    }

}