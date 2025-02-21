package aps.fithom.startandroidapp.data.local.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import aps.fithom.startandroidapp.domain.models.Category

@Dao
interface CategoryDao {

    @Query("select * from category")
    fun getAllCategory():LiveData<List<Category>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addListCategory(listCategory:List<Category>)

    @Query("delete from category")
    suspend fun clearListCategory()

    @Transaction
    suspend fun fetchCategoryList(listCategory: List<Category>){
        clearListCategory()
        addListCategory(listCategory)
    }

}