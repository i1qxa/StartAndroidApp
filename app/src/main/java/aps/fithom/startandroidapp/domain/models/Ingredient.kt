package aps.fithom.startandroidapp.domain.models

import android.os.Parcelable
import aps.fithom.startandroidapp.data.local.db.IngredientDBEntity
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Serializable
@Parcelize
data class Ingredient(
    val quantity: String,
    val unitOfMeasure: String,
    val description: String,
) : Parcelable{

    fun toIngredientDB(recipeId:Int) = IngredientDBEntity(0,recipeId, quantity, unitOfMeasure, description)

}
