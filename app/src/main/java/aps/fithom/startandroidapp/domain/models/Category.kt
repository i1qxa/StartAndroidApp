package aps.fithom.startandroidapp.domain.models

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import aps.fithom.startandroidapp.data.local.db.CategoryDBEntity
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Serializable
@Parcelize
data class Category(
    @PrimaryKey
    val id: Int,
    val title: String,
    val description: String,
    val imageUrl: String,
) : Parcelable{

    fun toCategoryDBEntity() = CategoryDBEntity(id, title, description, imageUrl)

}