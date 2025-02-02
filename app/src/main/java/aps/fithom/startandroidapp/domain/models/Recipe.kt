package aps.fithom.startandroidapp.domain.models

import android.os.Parcelable
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
) : Parcelable
