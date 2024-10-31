package aps.fithom.startandroidapp.ui.recipe

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import aps.fithom.startandroidapp.data.local.Recipe
import aps.fithom.startandroidapp.databinding.FragmentRecipeBinding
import aps.fithom.startandroidapp.ui.recipes_list.RecipesListFragment

class RecipeFragment : Fragment() {

    private var _binding: FragmentRecipeBinding? = null
    private val binding: FragmentRecipeBinding
        get() = _binding ?: throw IllegalStateException("FragmentRecipeBinding must not be null")
    private var recipe: Recipe? = null

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
        recipe?.let {
            binding.tvRecipeName.text = it.title
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}