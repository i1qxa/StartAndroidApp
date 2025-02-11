package aps.fithom.startandroidapp.ui.category_list

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import aps.fithom.startandroidapp.data.remote.RecipesRepository
import aps.fithom.startandroidapp.domain.models.Category
import kotlinx.coroutines.launch

class CategoryViewModel(private val application: Application) : AndroidViewModel(application) {

    private val _categoryStateLD = MutableLiveData<CategoryState>(CategoryState())
    val categoryStateLD: LiveData<CategoryState>
        get() = _categoryStateLD

    private val recipesRepository = RecipesRepository()

    init {
        updateCategoryList()
    }

    fun updateCategoryList() {
        viewModelScope.launch {
            val recipeList = recipesRepository.getCategories().await()
            _categoryStateLD.postValue(_categoryStateLD.value?.copy(categoryList = recipeList))
        }
    }

    data class CategoryState(
        val categoryList: List<Category>? = emptyList()
    )

}