package aps.fithom.startandroidapp.data.local.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction

@Dao
interface RecipeDao {

    @Query("select id from recipedbentity where isInFavorite = 1")
    fun getListFavoriteIdsLD(): LiveData<List<Int>>

    @Query("select isInFavorite from recipedbentity where id = :id")
    suspend fun getFavoriteStateById(id: Int): Boolean

    @Query("select isInFavorite from recipedbentity where id = :id")
    fun getFavoriteStateByIdLD(id: Int): LiveData<Boolean>

    @Query("select * from recipedbentity where id = :id")
    suspend fun getRecipesById(id: Int): RecipeDBEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addRecipes(recipes: List<RecipeDBEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateRecipe(recipe: RecipeDBEntity)

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun changeFavoriteState(recipeId: Int) {
        getRecipesById(recipeId).let {
            updateRecipe(it.copy(isInFavorite = !it.isInFavorite))
        }
    }

}