package aps.fithom.startandroidapp.data.local

import android.app.Application
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors


class MyApp : Application() {

    val executorService: ExecutorService by lazy { Executors.newFixedThreadPool(4) }

}