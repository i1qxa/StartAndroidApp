package aps.fithom.startandroidapp.data.local.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction

@Dao
interface IngredientsDao {

    @Query("select * from ingredientdbentity where recipeId in (:ids)")
    suspend fun getIngredientsByRecipeIds(ids: List<Int>): List<IngredientDBEntity>

    @Query("delete from ingredientdbentity where recipeId in (:ids)")
    suspend fun deleteIngredientsByRecipeIds(ids: List<Int>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addIngredients(ingredients: List<IngredientDBEntity>)

    @Transaction
    suspend fun fetchIngredients(recipeIds: List<Int>, ingredients: List<IngredientDBEntity>) {
        deleteIngredientsByRecipeIds(recipeIds)
        addIngredients(ingredients)
    }

}