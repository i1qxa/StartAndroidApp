package aps.fithom.startandroidapp.data.local.db

import androidx.room.Embedded
import androidx.room.Relation
import aps.fithom.startandroidapp.domain.models.Category


data class CategoryWithRecipes(
    @Embedded val category: Category,
    @Relation(
        parentColumn = "id",
        entityColumn = "categoryId"
    )
    val recipes: List<RecipeDBEntity>
)
