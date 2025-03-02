package aps.fithom.startandroidapp.data.local.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction

@Dao
interface RecipeDao {

    @Query("select * from recipedbentity where categoryId = :id")
    fun getRecipesWithIngredientsByCategoryId(id: Int): LiveData<List<RecipesWithIngredients>>

    @Query("select id from recipedbentity where isInFavorite = 1")
    suspend fun getListFavoriteIds():List<Int>

    @Query("select isInFavorite from recipedbentity where id = :id")
    suspend fun getFavoriteStateById(id:Int):Boolean

    @Query("select * from recipedbentity where id = :id")
    suspend fun getRecipesById(id: Int): RecipeDBEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addRecipes(recipes: List<RecipeDBEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateRecipe(recipe:RecipeDBEntity)

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun changeFavoriteState(recipeId: Int){
        getRecipesById(recipeId).let {
            updateRecipe(it.copy(isInFavorite = !it.isInFavorite))
        }
    }

    @Query("delete from recipedbentity where categoryId = :id")
    suspend fun deleteRecipesByCategoryId(id: Int)

    @Transaction
    suspend fun fetchRecipes(categoryId: Int, recipes: List<RecipeDBEntity>) {
        val listFavoriteIds = getListFavoriteIds()
        deleteRecipesByCategoryId(categoryId)
        addRecipes(recipes.map { if (it.id in listFavoriteIds) it.copy(isInFavorite = true) else it })
    }

}