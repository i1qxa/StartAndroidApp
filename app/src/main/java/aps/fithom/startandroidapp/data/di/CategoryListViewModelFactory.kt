package aps.fithom.startandroidapp.data.di

import aps.fithom.startandroidapp.data.remote.RecipesRepository
import aps.fithom.startandroidapp.ui.category_list.CategoryViewModel

class CategoryListViewModelFactory(private val recipesRepository: RecipesRepository):Factory<CategoryViewModel> {

    override fun create(): CategoryViewModel {
        return CategoryViewModel(recipesRepository)
    }
}