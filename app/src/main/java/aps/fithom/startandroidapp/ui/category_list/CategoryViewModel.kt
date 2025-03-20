package aps.fithom.startandroidapp.ui.category_list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import aps.fithom.startandroidapp.data.remote.RecipesRepository
import aps.fithom.startandroidapp.domain.models.Category
import kotlinx.coroutines.launch

class CategoryViewModel(private val recipesRepository:RecipesRepository) : ViewModel() {

    private val _categoryStateLD =
        recipesRepository.categoryListLD.switchMap { MutableLiveData(CategoryState(it)) }
    val categoryStateLD: LiveData<CategoryState>
        get() = _categoryStateLD


    init {
        viewModelScope.launch {
            recipesRepository.fetchCategoryList()
        }
    }

    data class CategoryState(
        val categoryList: List<Category>? = emptyList()
    )

}