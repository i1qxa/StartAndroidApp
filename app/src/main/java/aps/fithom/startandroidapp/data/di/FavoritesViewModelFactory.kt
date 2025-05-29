package aps.fithom.startandroidapp.data.di

import aps.fithom.startandroidapp.data.remote.RecipesRepository
import aps.fithom.startandroidapp.ui.recipes.favorites.FavoritesViewModel

class FavoritesViewModelFactory(private val recipesRepository: RecipesRepository) :
    Factory<FavoritesViewModel> {

    override fun create(): FavoritesViewModel {
        return FavoritesViewModel(recipesRepository)
    }
}