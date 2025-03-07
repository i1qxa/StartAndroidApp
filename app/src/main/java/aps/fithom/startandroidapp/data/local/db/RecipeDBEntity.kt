package aps.fithom.startandroidapp.data.local.db

import androidx.room.Entity
import androidx.room.ForeignKey
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
            onDelete = ForeignKey.CASCADE
        )
    ]
)
@TypeConverters(StringConverter::class)
data class RecipeDBEntity(
    @PrimaryKey
    val id: Int,
    val categoryId: Int,
    val title: String,
    val method: List<String>?,
    val imageUrl: String,
) {

    fun toRecipe() = Recipe(id, title, null, ArrayList(method?: emptyList()), imageUrl)

}
