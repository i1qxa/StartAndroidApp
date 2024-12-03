package aps.fithom.startandroidapp.domain.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Serializable
data class Category(
    val id: Int,
    val title: String,
    val description: String,
    val imageUrl: String,
)

@Parcelize
data class CategoryParcel(
    val id: Int,
    val title: String,
    val description: String,
    val imageUrl: String,
):Parcelable{

    fun getCategoryFromParcel():Category{
        return Category(
            id,
            title,
            description,
            imageUrl
        )
    }

    companion object{
        fun getInstanceFromCategory(category: Category):CategoryParcel{
            return CategoryParcel(
                category.id,
                category.title,
                category.description,
                category.imageUrl
            )
        }
    }
}
