package aps.fithom.startandroidapp.data.local.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import aps.fithom.startandroidapp.domain.models.Category
import aps.fithom.startandroidapp.domain.models.Recipe

@Dao
interface CategoryDao {

    @Query("select * from category")
    fun getAllCategory():LiveData<List<Category>>

    @Query("select * from category where id =:id")
    fun getCategoryWithRecipes(id:Int):LiveData<CategoryWithRecipes>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addListCategory(listCategory:List<Category>)

    @Query("delete from category")
    suspend fun clearListCategory()

    @Query("select id from recipedbentity where isInFavorite = 1")
    suspend fun getListFavoriteIds():List<Int>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addRecipes(recipes: List<RecipeDBEntity>)

    @Transaction
    suspend fun fetchCategoryList(listCategory: List<Category>, listRecipes:List<Recipe>){
        val listFavoriteIds = getListFavoriteIds()
        clearListCategory()

        addRecipes(recipes.map { if (it.id in listFavoriteIds) it.copy(isInFavorite = true) else it })
        addListCategory(listCategory)
    }

}