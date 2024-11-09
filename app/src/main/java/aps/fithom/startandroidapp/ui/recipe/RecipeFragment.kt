package aps.fithom.startandroidapp.ui.recipe

import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import aps.fithom.startandroidapp.R
import aps.fithom.startandroidapp.data.local.Recipe
import aps.fithom.startandroidapp.databinding.FragmentRecipeBinding
import aps.fithom.startandroidapp.ui.recipes_list.RecipesListFragment
import com.google.android.material.divider.MaterialDividerItemDecoration
import java.io.InputStream

class RecipeFragment : Fragment() {

    private var _binding: FragmentRecipeBinding? = null
    private val binding: FragmentRecipeBinding
        get() = _binding ?: throw IllegalStateException("FragmentRecipeBinding must not be null")
    private var recipe: Recipe? = null
    private var isInFavorite = false
    private val prefs by lazy {
        requireContext().getSharedPreferences(
            PREFS_NAME,
            Context.MODE_PRIVATE
        )
    }
    private var favoriteSet = hashSetOf<String>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRecipeBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireArguments().let { arguments ->
            recipe = if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) {
                arguments.getParcelable(RecipesListFragment.ARG_RECIPE)
            } else {
                arguments.getParcelable(RecipesListFragment.ARG_RECIPE, Recipe::class.java)
            }
        }
        if (savedInstanceState?.getStringArrayList(FAVORITE_SET) == null) {
            getFavorites()
        } else {
            savedInstanceState.getStringArrayList(FAVORITE_SET)?.let {
                favoriteSet.apply {
                    clear()
                    addAll(it)
                }
                setupFavoriteIcon()
            }
        }

        initUi()
        initRecycler()
    }

    private fun initRecycler() {

        recipe?.let { recipe ->

            val ingredientRVAdapter = IngredientListRVAdapter(recipe.ingredients)
            binding.tvPortionsAmount.text = (binding.sbPortionsAmount.progress).toString()
            binding.sbPortionsAmount.setOnSeekBarChangeListener(object :
                SeekBar.OnSeekBarChangeListener {
                override fun onProgressChanged(p0: SeekBar?, progress: Int, p2: Boolean) {
                    ingredientRVAdapter.updateIngredients(progress)
                    binding.tvPortionsAmount.text = progress.toString()
                }

                override fun onStartTrackingTouch(p0: SeekBar?) {
                }

                override fun onStopTrackingTouch(p0: SeekBar?) {
                }
            })

            val divider = MaterialDividerItemDecoration(
                requireContext(),
                LinearLayoutManager.VERTICAL
            ).apply {
                dividerColor = requireContext().getColor(R.color.divider_color)
                dividerThickness = resources.getDimensionPixelSize(R.dimen.divider_height)
                isLastItemDecorated = false
            }

            with(binding.rvIngredients) {
                adapter = ingredientRVAdapter
                addItemDecoration(divider)
            }
            val cookingMethodRVAdapter = CookingMethodListRVAdapter(recipe.method)
            with(binding.rvCookingMethods) {
                adapter = cookingMethodRVAdapter
                addItemDecoration(divider)
            }
        }
    }

    private fun initUi() {
        recipe?.let { recipe ->
            binding.tvRecipeName.text = recipe.title
            try {
                val inputStream: InputStream? =
                    requireContext().assets?.open(recipe.imageUrl)
                val drawable = Drawable.createFromStream(inputStream, null)
                binding.ivRecipeImg.setImageDrawable(drawable)
                binding.ivRecipeImg.contentDescription = "Img of ${recipe.title}"
            } catch (e: Exception) {
                Log.d("!!!", "Error loading img: ${e.message}")
            }
            with(binding.ibToFavorite) {
                setupFavoriteIcon()
                setOnClickListener {
                    recipe.id.toString().let { recipeId ->
                        if (favoriteSet.contains(recipeId)) {
                            favoriteSet.remove(recipeId)
                        } else {
                            favoriteSet.add(recipeId)
                        }
                        saveFavorites()
                        setupFavoriteIcon()
                    }
                }
            }
        }
    }

    private fun setupFavoriteIcon() {
        recipe?.let { recipe ->
            val imgResource = if (favoriteSet.contains(recipe.id.toString())) {
                R.drawable.ic_heart
            } else {
                R.drawable.ic_heart_empty
            }
            binding.ibToFavorite.setImageResource(imgResource)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putStringArrayList(FAVORITE_SET, ArrayList(favoriteSet))
    }

    private fun getFavorites() {
        prefs.getStringSet(PREFS_FAVORITE_SET, null)?.let { prefsFavoriteSet ->
            favoriteSet.apply {
                clear()
                addAll(prefsFavoriteSet)
            }
        }
    }

    private fun saveFavorites() {
        prefs.edit().putStringSet(PREFS_FAVORITE_SET, favoriteSet).apply()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        private const val FAVORITE_SET = "favorite_set"
        const val PREFS_NAME = "start_android_app_prefs"
        const val PREFS_FAVORITE_SET = "favorite_set"
    }

}