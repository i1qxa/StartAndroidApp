package aps.fithom.startandroidapp.ui.recipes.recipe

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import aps.fithom.startandroidapp.R
import aps.fithom.startandroidapp.databinding.FragmentRecipeBinding
import aps.fithom.startandroidapp.ui.recipes.recipes_list.RecipesListFragment
import com.google.android.material.divider.MaterialDividerItemDecoration

class RecipeFragment : Fragment() {

    private var _binding: FragmentRecipeBinding? = null
    private val binding: FragmentRecipeBinding
        get() = _binding ?: throw IllegalStateException("FragmentRecipeBinding must not be null")

    private val viewModel by viewModels<RecipeViewModel>()
    private var ingredientRVAdapter: IngredientListRVAdapter? = null
    private var cookingMethodRVAdapter: CookingMethodListRVAdapter? = null

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
            arguments.getInt(RecipesListFragment.ARG_RECIPE_ID).let {
                viewModel.loadRecipe(it)
            }
        }
        initUi()
        initRecycler()
    }

    private fun initRecycler() {

        binding.tvPortionsAmount.text = (binding.sbPortionsAmount.progress).toString()
        binding.sbPortionsAmount.setOnSeekBarChangeListener(object :
            SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(p0: SeekBar?, progress: Int, p2: Boolean) {
                viewModel.updatePortionAmount(progress)
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
        binding.rvIngredients.addItemDecoration(divider)
        binding.rvCookingMethods.addItemDecoration(divider)
    }

    private fun initUi() {
        viewModel.recipeStateLD.observe(viewLifecycleOwner) { recipeState ->
            recipeState.recipe?.let { recipe ->
                binding.tvRecipeName.text = recipe.title
                recipeState.recipeImage?.let { recipeImg ->
                    binding.ivRecipeImg.setImageDrawable(recipeImg)
                    binding.ivRecipeImg.contentDescription = "Img of ${recipe.title}"
                }
                with(binding.ibToFavorite) {
                    setOnClickListener {
                        viewModel.onFavoritesClicked()
                    }
                }
                ingredientRVAdapter = IngredientListRVAdapter(recipe.ingredients)
                binding.rvIngredients.adapter = ingredientRVAdapter
                cookingMethodRVAdapter = CookingMethodListRVAdapter(recipe.method)
                binding.rvCookingMethods.adapter = cookingMethodRVAdapter
            }
            ingredientRVAdapter?.updateIngredients(recipeState.portionAmount)
            binding.tvPortionsAmount.text = recipeState.portionAmount.toString()
            updateFavoriteIcon(recipeState.isInFavorite)
        }
    }

    private fun updateFavoriteIcon(isInFavorite: Boolean) {
        val imgResource = if (isInFavorite) {
            R.drawable.ic_heart
        } else {
            R.drawable.ic_heart_empty
        }
        binding.ibToFavorite.setImageResource(imgResource)
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        const val PREFS_NAME = "start_android_app_prefs"
        const val PREFS_FAVORITE_SET = "favorite_set"
    }

}
