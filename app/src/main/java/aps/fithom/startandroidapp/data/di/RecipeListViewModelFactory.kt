package aps.fithom.startandroidapp.data.di

import aps.fithom.startandroidapp.data.remote.RecipesRepository
import aps.fithom.startandroidapp.ui.recipes.recipes_list.RecipesListViewModel

class RecipeListViewModelFactory(private val recipesRepository: RecipesRepository) :
    Factory<RecipesListViewModel> {

    override fun create(): RecipesListViewModel {
        return RecipesListViewModel(recipesRepository)
    }
}