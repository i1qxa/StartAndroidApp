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

    @Query("select * from recipedbentity where id = :id")
    suspend fun getRecipesById(id: Int): RecipeDBEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addRecipes(recipes: List<RecipeDBEntity>)

    @Query("delete from recipedbentity where categoryId = :id")
    suspend fun deleteRecipesByCategoryId(id: Int)

    @Transaction
    suspend fun fetchRecipes(categoryId: Int, recipes: List<RecipeDBEntity>) {
        deleteRecipesByCategoryId(categoryId)
        addRecipes(recipes)
    }

}