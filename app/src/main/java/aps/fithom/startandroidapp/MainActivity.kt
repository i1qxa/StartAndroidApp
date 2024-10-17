package aps.fithom.startandroidapp

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.add
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import aps.fithom.startandroidapp.databinding.ActivityMainBinding

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
        }
    }

    private fun setupBtnClickListeners(){
        with(binding){
            btnCategory.setOnClickListener {
                launchCategoryListFragment()
            }
            btnFavorite.setOnClickListener {
                launchFavoritesFragment()
            }
        }
    }

    private fun launchCategoryListFragment(){
        supportFragmentManager.commit {
            setReorderingAllowed(true)
            addToBackStack(null)
            replace<CategoriesListFragment>(R.id.mainContainer)
        }
    }

    private fun launchFavoritesFragment(){
        supportFragmentManager.commit {
            setReorderingAllowed(true)
            addToBackStack(null)
            replace<FavoritesFragment>(R.id.mainContainer)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}