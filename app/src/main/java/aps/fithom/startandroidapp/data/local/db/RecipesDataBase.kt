package aps.fithom.startandroidapp.data.local.db

import android.app.Application
import androidx.room.Database
import androidx.room.Room
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

    companion object {
        private var INSTANCE: RecipesDataBase? = null
        private val LOCK = Any()
        private const val DB_NAME = "recipes_db"

        fun getInstance(application: Application): RecipesDataBase {
            INSTANCE?.let {
                return it
            }
            synchronized(LOCK) {
                INSTANCE?.let {
                    return it
                }
                val db = Room.databaseBuilder(
                    application,
                    RecipesDataBase::class.java,
                    DB_NAME
                )
                    .build()
                INSTANCE = db
                return db
            }
        }
    }
}