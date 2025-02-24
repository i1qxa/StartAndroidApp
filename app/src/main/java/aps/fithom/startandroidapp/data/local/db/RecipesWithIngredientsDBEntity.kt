package aps.fithom.startandroidapp.data.local.db

import androidx.room.Embedded
import androidx.room.Relation
import aps.fithom.startandroidapp.domain.models.Recipe


data class RecipesWithIngredientsDBEntity(
    @Embedded val recipeDB: RecipeDBEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "recipeId"
    )
    val ingredients: List<IngredientDBEntity>?
) {

    fun toRecipe() = Recipe(
        recipeDB.id,
        recipeDB.title,
        java.util.ArrayList(ingredients?.map { it.toIngredient() }),
        ArrayList(recipeDB.method ?: emptyList()),
        recipeDB.imageUrl
    )

}
