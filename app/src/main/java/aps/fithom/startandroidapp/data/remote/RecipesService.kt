package aps.fithom.startandroidapp.data.remote

import aps.fithom.startandroidapp.domain.models.Category
import aps.fithom.startandroidapp.domain.models.Recipe
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RecipesService {

    @GET("category")
    suspend fun getCategories(): Call<List<Category>>

    @GET("category/{categoryId}/recipes")
    suspend fun getRecipesByCategoryId(@Path("categoryId") categoryId: Int): Call<List<Recipe>>

    @GET("recipe/{recipeId}")
    suspend fun getRecipeById(@Path("recipeId") recipeId: Int): Call<Recipe?>

    @GET("recipes")
    suspend fun getRecipesByIds(@Query("ids") ids: String): Call<List<Recipe>>

}