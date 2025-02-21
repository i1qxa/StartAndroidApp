package aps.fithom.startandroidapp.data.local.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import aps.fithom.startandroidapp.domain.models.Category

@Entity
data class CategoryDBEntity(
    @PrimaryKey
    val id: Int,
    val title: String,
    val description: String,
    val imageUrl: String,
){
    fun toCategory() = Category(id, title, description, imageUrl)
}