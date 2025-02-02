package aps.fithom.startandroidapp.ui.main

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.findNavController
import aps.fithom.startandroidapp.R
import aps.fithom.startandroidapp.databinding.ActivityMainBinding
import aps.fithom.startandroidapp.domain.models.Category
import aps.fithom.startandroidapp.domain.models.Recipe
import com.google.gson.Gson
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.Executors

class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding
        get() = _binding
            ?: throw IllegalStateException("Binding for ActivityMainBinding must not be null")
    private val navController by lazy { findNavController(R.id.mainContainer) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        setupBtnClickListeners()
        val threadPool = Executors.newCachedThreadPool()
        val thread = Thread {
            val loggingInterceptor = HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            }
            val client = OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .build()
            val request = Request.Builder()
                .url("https://recipes.androidsprint.ru/api/category")
                .build()
            client.newCall(request).execute().use { response ->
                val categorysJson = response.body?.string()
                Log.d(LOG_TAG, "Response body: $categorysJson")
                Log.d(LOG_TAG, "Выполняю запрос на потоке: ${Thread.currentThread().name}")
                val gson = Gson()
                val categoryList = gson.fromJson(categorysJson, Array<Category>::class.java)
                categoryList.forEach {
                    Log.d(LOG_TAG, "Category: $it")
                }
                val listCategoryId = categoryList.map {
                    it.id
                }
                listCategoryId.forEach { categoryId ->
                    threadPool.submit {
                        val recipeRequest = Request.Builder()
                            .url("https://recipes.androidsprint.ru/api/category/${categoryId}/recipes")
                            .build()
                        client.newCall(recipeRequest).execute().use { response ->
                            var logMsg =
                                "Рецепты получены на потоке: ${Thread.currentThread().name}\n"
                            val recipeList =
                                gson.fromJson(response.body?.string(), Array<Recipe>::class.java)
                            logMsg += recipeList.joinToString("\n") { it.title }
                            Log.d(LOG_TAG, logMsg)
                        }
                    }
                }

            }
        }
        thread.start()
    }

    private fun setupBtnClickListeners() {
        with(binding) {
            btnCategory.setOnClickListener {
                navController.navigate(R.id.global_action_go_to_category_list)
            }
            btnFavorite.setOnClickListener {
                navController.navigate(R.id.global_action_go_to_favorites)
            }
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        const val LOG_TAG = "|||"
    }

}
