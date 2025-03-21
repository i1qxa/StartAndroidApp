package aps.fithom.startandroidapp.data.remote

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.switchMap
import aps.fithom.startandroidapp.data.local.db.RecipeDBEntity
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
    private val recipeDao = recipesDB.recipeDao()
    val categoryListLD = categoryDao.getAllCategory()
    private val selectedCategoryLD = MutableLiveData<Int>()
    val categoryWithRecipesLD =
        selectedCategoryLD.switchMap { categoryId -> categoryDao.getCategoryWithRecipes(categoryId) }
    private val favoriteIds = recipeDao.getListFavoriteIdsLD()
    val favoriteRecipes = favoriteIds.switchMap { favoriteIds ->
        liveData { emit(getRecipesByIds(favoriteIds.toSet())) }
    }
    private val selectedRecipeId = MutableLiveData<Int>()
    val isSelectedRecipeInFavoriteLD = selectedRecipeId.switchMap { recipeDao.getFavoriteStateByIdLD(it) }


    suspend fun fetchCategoryList() {
        withContext(defaultDispatcher) {
            getCategoriesFromApi().await()?.let { categoryList ->
                val recipeList = mutableListOf<RecipeDBEntity>()
                categoryList.forEach { categoryId ->
                    getRecipesByCategoryId(categoryId.id).await()?.let {
                        recipeList.addAll(it.map { it.toRecipeDB(categoryId.id) })
                    }
                }
                categoryDao.fetchCategoryList(categoryList, recipeList)
            }
        }
    }

    fun selectRecipeId(recipeId:Int){
        selectedRecipeId.value = recipeId
    }

    fun selectCategoryById(categoryId: Int) {
        selectedCategoryLD.value = categoryId
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

    suspend fun getRecipeById(recipeId: Int): Recipe? = withContext(defaultDispatcher) {
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

    suspend fun changeRecipeFavoriteStateById(recipeId: Int) {
        withContext(defaultDispatcher) {
            recipeDao.changeFavoriteState(recipeId)
        }
    }

    suspend fun getFavoriteStateByRecipeId(recipeId: Int) = withContext(defaultDispatcher) {
        recipeDao.getFavoriteStateById(recipeId)
    }

}