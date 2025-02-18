package aps.fithom.startandroidapp.data.remote

import aps.fithom.startandroidapp.domain.models.Category
import aps.fithom.startandroidapp.domain.models.Recipe
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext

class RecipesRepository(private val defaultDispatcher: CoroutineDispatcher = Dispatchers.IO) {

    private val recipeService = RetrofitClient.instance

    suspend fun getCategories(): Deferred<List<Category>?> = withContext(defaultDispatcher) {
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