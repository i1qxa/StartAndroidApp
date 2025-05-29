package aps.fithom.startandroidapp.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import aps.fithom.startandroidapp.domain.models.Category

@Database(
    entities = [
        Category::class,
        RecipeDBEntity::class,
    ],
    exportSchema = false,
    version = 1,
)
@TypeConverters(StringConverter::class)
abstract class RecipesDataBase : RoomDatabase() {

    abstract fun categoryDao(): CategoryDao
    abstract fun recipeDao(): RecipeDao

}