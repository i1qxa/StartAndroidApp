package aps.fithom.startandroidapp.data.local

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.Log
import aps.fithom.startandroidapp.data.remote.RetrofitClient
import java.io.InputStream

fun Context.getDrawableOrNullFromAssetsByPath(path: String): Drawable? {
    return try {
        val inputStream: InputStream? =
            this.assets?.open(path)
        Drawable.createFromStream(inputStream, null)
    } catch (e: Exception) {
        Log.e("!!!", "Error loading img: ${e.message}")
        null
    }
}

fun getFullImgPathByImgName(imgName: String): String {
    return "${RetrofitClient.BASE_URL}images/${imgName}"
}