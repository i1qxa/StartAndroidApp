package aps.fithom.startandroidapp.ui.recipes.favorites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import aps.fithom.startandroidapp.databinding.FragmentFavoritesBinding
import aps.fithom.startandroidapp.ui.recipes.recipes_list.RecipeListRVAdapter

class FavoritesFragment : Fragment() {

    private var _binding: FragmentFavoritesBinding? = null
    private val binding
        get() = _binding ?: throw IllegalStateException("FragmentFavoritesBinding must not be null")
    private val viewModel by viewModels<FavoritesViewModel>()
    private val recipesListRVAdapter by lazy { RecipeListRVAdapter() }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoritesBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.updateFavoriteState()
        initRecycler()
        initUi()
    }

    private fun initUi() {
        viewModel.favoriteStateLD.observe(viewLifecycleOwner) { favoritesState ->
            favoritesState.recipesList.let { recipeList ->
                if (recipeList.isEmpty()) {
                    binding.rvRecipesFavorite.visibility = View.GONE
                    binding.tvFavoritesIsEmpty.visibility = View.VISIBLE
                } else {
                    recipesListRVAdapter.updateRecipesList(recipeList)
                    binding.tvFavoritesIsEmpty.visibility = View.GONE
                    binding.rvRecipesFavorite.visibility = View.VISIBLE
                }
            }
        }
    }

    private fun initRecycler() {
        recipesListRVAdapter.setOnRecipeItemClickListener(object :
            RecipeListRVAdapter.OnRecipeItemClickListener {
            override fun onItemClick(recipeId: Int) {
                FavoritesFragmentDirections.actionFavoritesFragmentToRecipeFragment(recipeId)
                    .apply {
                        findNavController().navigate(this)
                    }
            }
        })
        binding.rvRecipesFavorite.adapter = recipesListRVAdapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}