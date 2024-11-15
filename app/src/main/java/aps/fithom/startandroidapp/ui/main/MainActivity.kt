package aps.fithom.startandroidapp.ui.main

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.add
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import aps.fithom.startandroidapp.R
import aps.fithom.startandroidapp.databinding.ActivityMainBinding
import aps.fithom.startandroidapp.ui.category_list.CategoriesListFragment
import aps.fithom.startandroidapp.ui.favorites.FavoritesFragment

class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding
        get() = _binding
            ?: throw IllegalStateException("Binding for ActivityMainBinding must not be null")

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
        if (savedInstanceState == null) initCategoryListFragment()
        setupBtnClickListeners()
    }

    private fun initCategoryListFragment() {
        supportFragmentManager.commit {
            setReorderingAllowed(true)
            add<CategoriesListFragment>(R.id.mainContainer)
            addToBackStack(null)
        }
    }

    private fun setupBtnClickListeners() {
        with(binding) {
            btnCategory.setOnClickListener {
                replaceFragment<CategoriesListFragment>()
            }
            btnFavorite.setOnClickListener {
                replaceFragment<FavoritesFragment>()
            }
        }
    }

    private inline fun <reified T : Fragment> replaceFragment() {
        supportFragmentManager.commit {
            setReorderingAllowed(true)
            replace<T>(binding.mainContainer.id)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}