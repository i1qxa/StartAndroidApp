package aps.fithom.startandroidapp.data.local.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import aps.fithom.startandroidapp.domain.models.Category
import aps.fithom.startandroidapp.domain.models.Recipe
import java.util.ArrayList

@Entity(
    foreignKeys = [
        ForeignKey(
            entity = Category::class,
            parentColumns = ["id"],
            childColumns = ["categoryId"],
            onDelete =  ForeignKey.CASCADE
        )
    ],
    indices = [Index(value = ["categoryId"])]
)
@TypeConverters(StringConverter::class)
data class RecipeDBEntity(
    @PrimaryKey
    val id: Int,
    val categoryId: Int,
    val title: String,
    val method: List<String>?,
    val imageUrl: String,
    @ColumnInfo(defaultValue = "0")
    val inFavorite: Boolean = false
) {

    fun toRecipe() = Recipe(id, title, null, ArrayList(method ?: emptyList()), imageUrl)

    companion object {
        const val EMPTY_CATEGORY_ID = -1
    }

}
