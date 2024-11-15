package aps.fithom.startandroidapp.ui.favorites

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import aps.fithom.startandroidapp.R
import aps.fithom.startandroidapp.data.local.Recipe
import aps.fithom.startandroidapp.data.local.STUB
import aps.fithom.startandroidapp.databinding.FragmentFavoritesBinding
import aps.fithom.startandroidapp.ui.recipe.RecipeFragment
import aps.fithom.startandroidapp.ui.recipe.RecipeFragment.Companion.PREFS_FAVORITE_SET
import aps.fithom.startandroidapp.ui.recipe.RecipeFragment.Companion.PREFS_NAME
import aps.fithom.startandroidapp.ui.recipes_list.RecipeListRVAdapter
import aps.fithom.startandroidapp.ui.recipes_list.RecipesListFragment.Companion.ARG_RECIPE

class FavoritesFragment : Fragment() {

    private var _binding: FragmentFavoritesBinding? = null
    private val binding
        get() = _binding ?: throw IllegalStateException("FragmentFavoritesBinding must not be null")

    private val prefs by lazy {
        requireContext().getSharedPreferences(
            PREFS_NAME,
            Context.MODE_PRIVATE
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoritesBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecycler()
    }

    private fun getFavoritesFromPrefs(): Set<Int>? {
        return prefs.getStringSet(PREFS_FAVORITE_SET, null)?.map {
            it.toInt()
        }?.toSet()
    }

    private fun initRecycler() {
        val recipeList = getFavoritesFromPrefs()?.let { setIds ->
            STUB.getRecipesByIds(setIds)
        }
        if (recipeList.isNullOrEmpty()) {
            binding.tvFavoritesIsEmpty.visibility = View.VISIBLE
        } else {
            val recycler = binding.rvRecipesFavorite
            val rvAdapter = RecipeListRVAdapter(recipeList)
            rvAdapter.setOnRecipeItemClickListener(object :
                RecipeListRVAdapter.OnRecipeItemClickListener {
                override fun onItemClick(recipeId: Int) {
                    STUB.getRecipeById(recipeId)?.let { recipe ->
                        openRecipeByBundle(recipe)
                    }
                }
            })
            recycler.adapter = rvAdapter
            recycler.visibility = View.VISIBLE
        }
    }

    private fun openRecipeByBundle(recipe: Recipe) {
        val bundle = bundleOf(
            ARG_RECIPE to recipe
        )
        parentFragmentManager.commit {
            setReorderingAllowed(true)
            replace<RecipeFragment>(R.id.mainContainer, args = bundle)
            addToBackStack(null)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}