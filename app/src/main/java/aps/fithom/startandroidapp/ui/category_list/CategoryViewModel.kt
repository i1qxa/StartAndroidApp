package aps.fithom.startandroidapp.ui.category_list

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import aps.fithom.startandroidapp.data.remote.RecipesRepository
import aps.fithom.startandroidapp.domain.models.Category

class CategoryViewModel(private val application: Application) : AndroidViewModel(application) {

    private val _categoryStateLD = MutableLiveData<CategoryState>(CategoryState())
    val categoryStateLD: LiveData<CategoryState>
        get() = _categoryStateLD

    private val recipesRepository = RecipesRepository(application)

    init {
        updateCategoryList()
    }

    fun updateCategoryList() {
        val recipeList = recipesRepository.getCategories()
        if (recipeList == null) {
            Toast.makeText(
                application.applicationContext,
                "Ошибка получения списка категорий",
                Toast.LENGTH_SHORT
            ).show()
        } else {
            _categoryStateLD.value = _categoryStateLD.value?.copy(categoryList = recipeList)
        }
    }

    data class CategoryState(
        val categoryList: List<Category> = emptyList()
    )

}