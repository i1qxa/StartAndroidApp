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

    @Query("select * from categorydbentity")
    fun getAllCategory():LiveData<List<CategoryDBEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addListCategory(listCategory:List<CategoryDBEntity>)

    @Query("delete from categorydbentity")
    suspend fun clearListCategory()

    @Transaction
    suspend fun fetchCategoryList(listCategory: List<CategoryDBEntity>){
        clearListCategory()
        addListCategory(listCategory)
    }

}