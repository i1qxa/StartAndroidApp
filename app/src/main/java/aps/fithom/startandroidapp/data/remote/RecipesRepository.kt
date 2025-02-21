package aps.fithom.startandroidapp.data.remote

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.switchMap
import aps.fithom.startandroidapp.data.local.db.RecipesDataBase
import aps.fithom.startandroidapp.domain.models.Category
import aps.fithom.startandroidapp.domain.models.Recipe
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext

class RecipesRepository(
    application: Application,
    private val defaultDispatcher: CoroutineDispatcher = Dispatchers.IO
) {

    private val recipeService = RetrofitClient.instance
    private val recipesDB = RecipesDataBase.getInstance(application)
    private val categoryDao = recipesDB.categoryDao()
    val categoryListLD = categoryDao.getAllCategory()
        .switchMap { categoryDBEntities -> MutableLiveData(categoryDBEntities.map { categoryDBEntity -> categoryDBEntity.toCategory() }) }

    suspend fun fetchCategoryList() {
        withContext(defaultDispatcher) {
            getCategoriesFromApi().await()?.let { categoryList ->
                categoryDao.fetchCategoryList(categoryList.map { it.toCategoryDBEntity() })
            }
        }
    }

    suspend fun getCategoriesFromApi(): Deferred<List<Category>?> = withContext(defaultDispatcher) {
        async {
            recipeService.getCategories().let { call ->
                call.execute().let { response ->
                    if (response.isSuccessful) {
                        response.body()
                    } else {
                        null
                    }
                }
            }
        }
    }

    suspend fun getRecipesByCategoryId(categoryId: Int): Deferred<List<Recipe>?> =
        withContext(defaultDispatcher) {
            async {
                recipeService.getRecipesByCategoryId(categoryId).let { call ->
                    call.execute().let { response ->
                        if (response.isSuccessful) {
                            response.body()
                        } else {
                            null
                        }
                    }
                }
            }
        }

    suspend fun getRecipesByIds(ids: Set<Int>): Deferred<List<Recipe>?> =
        withContext(defaultDispatcher) {
            async {
                recipeService.getRecipesByIds(ids.joinToString(separator = ",")).let { call ->
                    call.execute().let { response ->
                        if (response.isSuccessful) {
                            response.body()
                        } else {
                            null
                        }
                    }
                }
            }
        }

    suspend fun getRecipeById(recipeId: Int): Deferred<Recipe?> = withContext(defaultDispatcher) {
        async {
            recipeService.getRecipeById(recipeId).let { call ->
                call.execute().let { response ->
                    if (response.isSuccessful) {
                        response.body()
                    } else {
                        null
                    }
                }
            }
        }
    }
}