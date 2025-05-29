package aps.fithom.startandroidapp.data.di

import aps.fithom.startandroidapp.data.remote.RecipesRepository
import aps.fithom.startandroidapp.ui.recipes.recipe.RecipeViewModel

class RecipesViewModelFactory(private val recipesRepository: RecipesRepository) :
    Factory<RecipeViewModel> {

    override fun create(): RecipeViewModel {
        return RecipeViewModel(recipesRepository)
    }
}