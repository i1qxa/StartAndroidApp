package aps.fithom.startandroidapp.data.di

import android.app.Application
import aps.fithom.startandroidapp.data.local.db.CategoryDao
import aps.fithom.startandroidapp.data.local.db.RecipeDao
import aps.fithom.startandroidapp.data.local.db.RecipesDataBase
import aps.fithom.startandroidapp.data.remote.RecipesService
import aps.fithom.startandroidapp.data.remote.RetrofitClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class RecipeModule {

//    @Provides
//    fun provideApplication(): Application =
//        HiltAndroidApp::class.java.getDeclaredField("application").let { field ->
//            field.isAccessible = true
//            field.get(null) as RecipesApplication
//        }

    @Provides
    fun provideDatabase(application: Application): RecipesDataBase =
        RecipesDataBase.getInstance(application)

    @Provides
    fun provideCategoryDao(recipeDB: RecipesDataBase): CategoryDao = recipeDB.categoryDao()

    @Provides
    fun provideRecipeDao(recipeDB: RecipesDataBase): RecipeDao = recipeDB.recipeDao()

    @Provides
    fun provideRecipesService(): RecipesService = RetrofitClient.instance


}