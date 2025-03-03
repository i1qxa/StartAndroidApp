package aps.fithom.startandroidapp.domain.models

import android.os.Parcelable
import aps.fithom.startandroidapp.data.local.db.RecipeDBEntity
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Serializable
@Parcelize
data class Recipe(
    val id: Int,
    val title: String,
    val ingredients: ArrayList<Ingredient>?,
    val method: ArrayList<String>?,
    val imageUrl: String,
) : Parcelable{

    fun toRecipeDB(categoryId:Int) = RecipeDBEntity(id, categoryId, title, method, imageUrl)

}
