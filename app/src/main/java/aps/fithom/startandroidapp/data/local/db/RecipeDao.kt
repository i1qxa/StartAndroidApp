package aps.fithom.startandroidapp.data.local.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import aps.fithom.startandroidapp.domain.models.Recipe

@Dao
interface RecipeDao {

    @Query("select * from recipedbentity where categoryId = :id")
    fun getRecipesWithIngredientsByCategoryId(id: Int): LiveData<List<RecipesWithIngredientsDBEntity>>

    @Query("select * from recipedbentity where id = :id")
    suspend fun getRecipesById(id: Int): RecipeDBEntity?

    @Query("select id from recipedbentity where inFavorite = 1")
    suspend fun getFavoriteIds(): List<Int>

    @Query("select * from recipedbentity where id =:id")
    suspend fun getCategoryIdByRecipeId(id: Int): RecipesWithIngredientsDBEntity?

    @Query("select * from recipedbentity where id = :id limit 1")
    fun getRecipeWithIngredientById(id:Int):LiveData<RecipesWithIngredientsDBEntity>

    @Query("select * from recipedbentity where inFavorite = 1")
    fun getFavoriteRecipes(): LiveData<List<RecipesWithIngredientsDBEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addRecipes(recipes: List<RecipeDBEntity>)

    @Query("delete from recipedbentity where categoryId = :id")
    suspend fun deleteRecipesByCategoryId(id: Int)

    @Query("delete from ingredientdbentity where recipeId in (:ids)")
    suspend fun deleteIngredientsByRecipeIds(ids: List<Int>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addIngredients(ingredients: List<IngredientDBEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addOrUpdateRecipe(recipe: RecipeDBEntity)

    @Transaction
    suspend fun changeInFavoriteState(recipeId:Int) {
        getRecipesById(recipeId)?.let {
            addOrUpdateRecipe(it.copy(inFavorite = !it.inFavorite))
        }
    }

    @Transaction
    suspend fun fetchIngredients(recipesList: List<RecipesWithIngredientsDBEntity>) {
        deleteIngredientsByRecipeIds(recipesList.map { it.recipeDB.id })
        val listIngredients = mutableListOf<IngredientDBEntity>()
        recipesList.forEach {
            it.ingredients?.let { it1 -> listIngredients.addAll(it1) }
        }
        addIngredients(listIngredients)
    }

    @Transaction
    suspend fun fetchRecipes(categoryId: Int, recipes: List<RecipesWithIngredientsDBEntity>) {
        val favoriteIds = getFavoriteIds()
        deleteRecipesByCategoryId(categoryId)
        addRecipes(recipes.map { if (it.recipeDB.id in favoriteIds) it.recipeDB.copy(inFavorite = true) else it.recipeDB })
        fetchIngredients(recipes)
    }

    @Transaction
    suspend fun fetchRecipe(recipe: Recipe) {
        getCategoryIdByRecipeId(recipe.id)?.let { savedRecipe ->
            recipe.toRecipeDBWithIngredients(savedRecipe.recipeDB.categoryId).let {
                fetchRecipes(
                    savedRecipe.recipeDB.categoryId,
                    listOf(it.copy(recipeDB = it.recipeDB.copy(inFavorite = savedRecipe.recipeDB.inFavorite)))
                )
            }
        }
    }

}