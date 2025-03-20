package aps.fithom.startandroidapp.data.di

import android.app.Application
import aps.fithom.startandroidapp.data.local.db.RecipesDataBase
import aps.fithom.startandroidapp.data.remote.RecipesRepository
import aps.fithom.startandroidapp.data.remote.RetrofitClient

class AppContainer(application: Application) {

    private val recipeService = RetrofitClient.instance
    private val recipesDB = RecipesDataBase.getInstance(application)
    private val categoryDao = recipesDB.categoryDao()
    private val recipeDao = recipesDB.recipeDao()

    private val repository = RecipesRepository(recipeService, categoryDao, recipeDao)
    val categoryListViewModelFactory = CategoryListViewModelFactory(repository)
    val favoritesViewModelFactory = FavoritesViewModelFactory(repository)
    val recipesViewModelFactory = RecipesViewModelFactory(repository)
    val recipeListViewModelFactory = RecipeListViewModelFactory(repository)
}