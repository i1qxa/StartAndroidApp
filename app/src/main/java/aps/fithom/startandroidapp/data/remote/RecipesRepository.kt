package aps.fithom.startandroidapp.data.remote

import android.app.Application
import android.util.Log
import aps.fithom.startandroidapp.data.local.MyApp
import aps.fithom.startandroidapp.domain.models.Category
import aps.fithom.startandroidapp.domain.models.Recipe
import java.util.concurrent.Callable

class RecipesRepository(application: Application) {

    private val executorService = (application as? MyApp)?.executorService
    private val recipeService = RetrofitClient.instance

    fun getCategories(): List<Category>? {
        executorService?.let { executorService ->
            val future = executorService.submit(Callable {
                recipeService.getCategories().let { call ->
                    call.execute().let { response ->
                        if (response.isSuccessful) {
                            response.body()
                        } else {
                            null
                        }
                    }
                }
            })
            return try {
                future.get()
            } catch (e: Exception) {
                Log.d("|||", e.message.toString())
                null
            }
        }
        return null
    }

    fun getRecipesByCategoryId(categoryId: Int): List<Recipe>? {
        executorService?.let { executorService ->
            val future = executorService.submit(Callable {
                recipeService.getRecipesByCategoryId(categoryId).let { call ->
                    call.execute().let { response ->
                        if (response.isSuccessful) {
                            response.body()
                        } else {
                            null
                        }
                    }
                }
            })
            return try {
                future.get()
            } catch (e: Exception) {
                Log.d("|||", e.message.toString())
                null
            }
        }
        return null
    }

    fun getRecipesByIds(ids: Set<Int>): List<Recipe>? {
        executorService?.let { executorService ->
            val future = executorService.submit(Callable {
                recipeService.getRecipesByIds(ids.joinToString(separator = ",")).let { call ->
                    call.execute().let { response ->
                        if (response.isSuccessful) {
                            response.body()
                        } else {
                            null
                        }
                    }
                }
            })
            return try {
                future.get()
            } catch (e: Exception) {
                Log.d("|||", e.message.toString())
                null
            }
        }
        return null
    }

    fun getRecipeById(recipeId: Int): Recipe? {
        executorService?.let { executorService ->
            val future = executorService.submit(Callable {
                recipeService.getRecipeById(recipeId).let { call ->
                    call.execute().let { response ->
                        if (response.isSuccessful) {
                            response.body()
                        } else {
                            null
                        }
                    }
                }
            })
            return try {
                future.get()
            } catch (e: Exception) {
                Log.d("|||", e.message.toString())
                null
            }
        }
        return null
    }

}