package aps.fithom.startandroidapp.data.local.db

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import aps.fithom.startandroidapp.domain.models.Ingredient

@Entity(
    foreignKeys = [
        ForeignKey(
            entity = RecipeDBEntity::class,
            parentColumns = ["id"],
            childColumns = ["recipeId"],
        )
    ]
)
data class IngredientDBEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val recipeId: Int,
    val quantity: String,
    val unitOfMeasure: String,
    val description: String,
) {

    fun toIngredient() = Ingredient(quantity, unitOfMeasure, description)

}
