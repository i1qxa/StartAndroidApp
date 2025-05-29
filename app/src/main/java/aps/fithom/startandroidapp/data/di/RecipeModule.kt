package aps.fithom.startandroidapp.data.di

import android.content.Context
import androidx.room.Room
import aps.fithom.startandroidapp.data.local.db.CategoryDao
import aps.fithom.startandroidapp.data.local.db.RecipeDao
import aps.fithom.startandroidapp.data.local.db.RecipesDataBase
import aps.fithom.startandroidapp.data.remote.RecipesService
import aps.fithom.startandroidapp.data.remote.RetrofitClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RecipeModule {

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): RecipesDataBase =
        Room.databaseBuilder(
            context,
            RecipesDataBase::class.java,
            "recipes_db"
        ).build()

    @Singleton
    @Provides
    fun provideCategoryDao(recipeDB: RecipesDataBase): CategoryDao = recipeDB.categoryDao()

    @Singleton
    @Provides
    fun provideRecipeDao(recipeDB: RecipesDataBase): RecipeDao = recipeDB.recipeDao()

    @Provides
    fun provideRecipesService(): RecipesService = RetrofitClient.instance

}