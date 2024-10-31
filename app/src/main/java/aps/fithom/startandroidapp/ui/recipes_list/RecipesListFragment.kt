package aps.fithom.startandroidapp.ui.recipes_list

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import aps.fithom.startandroidapp.R
import aps.fithom.startandroidapp.data.local.STUB
import aps.fithom.startandroidapp.databinding.FragmentRecipesListBinding
import aps.fithom.startandroidapp.ui.category_list.ARG_CATEGORY_ID
import aps.fithom.startandroidapp.ui.category_list.ARG_CATEGORY_IMAGE_URL
import aps.fithom.startandroidapp.ui.category_list.ARG_CATEGORY_NAME
import aps.fithom.startandroidapp.ui.recipe.RecipeFragment
import java.io.InputStream

class RecipesListFragment : Fragment() {

    private var _binding: FragmentRecipesListBinding? = null
    private val binding: FragmentRecipesListBinding
        get() = _binding
            ?: throw (IllegalStateException("FragmentRecipesListBinding must not be null"))
    private var categoryId: Int? = null
    private var categoryName: String? = null
    private var categoryImageUrl: String? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRecipesListBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireArguments().let { arguments ->
            categoryId = arguments.getInt(ARG_CATEGORY_ID)
            categoryName = arguments.getString(ARG_CATEGORY_NAME)
            categoryImageUrl = arguments.getString(ARG_CATEGORY_IMAGE_URL)
        }
        categoryName?.let { categoryName ->
            binding.tvCategoryName.text = categoryName
            binding.ivCategoryImg.contentDescription =
                getString(R.string.content_description_img_of_category, categoryName)
        }
        categoryImageUrl?.let { categoryImg ->
            try {
                val inputStream: InputStream? =
                    requireContext().assets?.open(categoryImg)
                val drawable = Drawable.createFromStream(inputStream, null)
                binding.ivCategoryImg.setImageDrawable(drawable)
            } catch (e: Exception) {
                Log.d("!!!", "Error loading img: ${e.message}")
            }
        }
        initRecycler()
    }

    private fun initRecycler() {
        categoryId?.let { categoryId ->
            val recycler = binding.rvRecipes
            val rvAdapter = RecipeListRVAdapter(STUB.getRecipesByCategoryId(categoryId))
            rvAdapter.setOnRecipeItemClickListener(object :
                RecipeListRVAdapter.OnRecipeItemClickListener {
                override fun onItemClick(recipeId: Int) {
                    val bundle = bundleOf(
                        ARG_RECIPE_ID to recipeId
                    )
                    openRecipeById(bundle)
                }
            })
            recycler.adapter = rvAdapter
        }
    }

    private fun openRecipeById(args: Bundle) {
        parentFragmentManager.commit {
            setReorderingAllowed(true)
            replace<RecipeFragment>(R.id.mainContainer)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object{
        const val ARG_RECIPE_ID = "recipe_id"
    }

}