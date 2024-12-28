package aps.fithom.startandroidapp.ui.recipes.recipes_list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import aps.fithom.startandroidapp.R
import aps.fithom.startandroidapp.databinding.FragmentRecipesListBinding

class RecipesListFragment : Fragment() {

    private var _binding: FragmentRecipesListBinding? = null
    private val binding: FragmentRecipesListBinding
        get() = _binding
            ?: throw (IllegalStateException("FragmentRecipesListBinding must not be null"))
    private val viewModel by viewModels<RecipesListViewModel>()
    private val recipesListRVAdapter = RecipeListRVAdapter()
    private val args by navArgs<RecipesListFragmentArgs>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRecipesListBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.loadCategoryAndUpdateRecipesList(args.Category)
        initUi()
        initRecycler()
    }

    private fun initUi() {
        viewModel.recipesListStateLD.observe(viewLifecycleOwner) { recipesListState ->
            recipesListState.category?.title.let { categoryTitle ->
                binding.tvCategoryName.text = categoryTitle
                binding.ivCategoryImg.contentDescription =
                    getString(R.string.content_description_img_of_category, categoryTitle)
            }
            recipesListState.categoryImg?.let { categoryImg ->
                binding.ivCategoryImg.setImageDrawable(categoryImg)
            }
            recipesListState.recipesList.let { recipes ->
                recipesListRVAdapter.updateRecipesList(recipes)
            }
        }
    }

    private fun initRecycler() {
        val recycler = binding.rvRecipes
        recipesListRVAdapter.setOnRecipeItemClickListener(object :
            RecipeListRVAdapter.OnRecipeItemClickListener {
            override fun onItemClick(recipeId: Int) {
                findNavController().navigate(
                    RecipesListFragmentDirections.actionRecipesListFragmentToRecipeFragment(
                        recipeId
                    )
                )
            }
        })
        recycler.adapter = recipesListRVAdapter
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}