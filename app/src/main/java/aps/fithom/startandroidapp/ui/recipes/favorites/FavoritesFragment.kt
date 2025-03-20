package aps.fithom.startandroidapp.ui.recipes.favorites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import aps.fithom.startandroidapp.R
import aps.fithom.startandroidapp.RecipesApplication
import aps.fithom.startandroidapp.databinding.FragmentFavoritesBinding
import aps.fithom.startandroidapp.ui.recipes.recipes_list.RecipeListRVAdapter

class FavoritesFragment : Fragment() {

    private var _binding: FragmentFavoritesBinding? = null
    private val binding
        get() = _binding ?: throw IllegalStateException("FragmentFavoritesBinding must not be null")
    private lateinit var viewModel: FavoritesViewModel
    private val recipesListRVAdapter by lazy { RecipeListRVAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel =
            (requireActivity().application as RecipesApplication).appContainer.favoritesViewModelFactory.create()
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
        initUi()
    }

    private fun initUi() {
        viewModel.favoriteStateLD.observe(viewLifecycleOwner) { favoritesState ->
            favoritesState.recipesList.let { recipeList ->
                if (recipeList == null) {
                    Toast.makeText(
                        requireContext(),
                        getString(R.string.error_loading_favorite_recipes),
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
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
    }

    private fun initRecycler() {
        recipesListRVAdapter.setOnRecipeItemClickListener(object :
            RecipeListRVAdapter.OnRecipeItemClickListener {
            override fun onItemClick(recipeId: Int) {
                findNavController().navigate(
                    FavoritesFragmentDirections.actionFavoritesFragmentToRecipeFragment(
                        recipeId
                    )
                )
            }
        })
        binding.rvRecipesFavorite.adapter = recipesListRVAdapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}