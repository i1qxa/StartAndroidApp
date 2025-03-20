package aps.fithom.startandroidapp

import android.app.Application
import aps.fithom.startandroidapp.data.di.AppContainer

class RecipesApplication:Application() {

    val appContainer by lazy { AppContainer(this) }

}