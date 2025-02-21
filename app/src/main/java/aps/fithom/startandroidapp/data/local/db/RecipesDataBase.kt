package aps.fithom.startandroidapp.data.local.db

import android.app.Application
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import aps.fithom.startandroidapp.domain.models.Category
import kotlinx.coroutines.InternalCoroutinesApi

@Database(
    entities = [
        CategoryDBEntity::class,
    ],
    exportSchema = false,
    version = 1
)
abstract class RecipesDataBase : RoomDatabase() {

    abstract fun categoryDao(): CategoryDao

    companion object {
        private var INSTANCE: RecipesDataBase? = null
        private val LOCK = Any()
        private const val DB_NAME = "recipes_db"

        @OptIn(InternalCoroutinesApi::class)
        fun getInstance(application: Application): RecipesDataBase {
            INSTANCE?.let {
                return it
            }
            kotlinx.coroutines.internal.synchronized(LOCK) {
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