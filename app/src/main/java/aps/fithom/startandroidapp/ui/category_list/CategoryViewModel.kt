package aps.fithom.startandroidapp.ui.category_list

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import aps.fithom.startandroidapp.data.local.STUB
import aps.fithom.startandroidapp.domain.models.Category

class CategoryViewModel(application: Application) : AndroidViewModel(application) {

    private val _categoryStateLD = MutableLiveData<CategoryState>(CategoryState())
    val categoryStateLD: LiveData<CategoryState>
        get() = _categoryStateLD

    init {
        updateCategoryList()
    }

    fun updateCategoryList() {
        _categoryStateLD.value = _categoryStateLD.value?.copy(categoryList = STUB.getCategories())
    }

    data class CategoryState(
        val categoryList: List<Category> = emptyList()
    )

}