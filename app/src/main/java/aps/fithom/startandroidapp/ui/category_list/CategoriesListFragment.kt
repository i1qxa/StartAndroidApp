package aps.fithom.startandroidapp.ui.category_list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.add
import androidx.fragment.app.commit
import androidx.fragment.app.viewModels
import aps.fithom.startandroidapp.R
import aps.fithom.startandroidapp.databinding.FragmentCategoriesListBinding
import aps.fithom.startandroidapp.ui.recipes.recipes_list.RecipesListFragment

const val ARG_CATEGORY_ID = "category_id"
//const val ARG_CATEGORY_NAME = "category_name"
//const val ARG_CATEGORY_IMAGE_URL = "category_img_url"

class CategoriesListFragment : Fragment() {

    private var _binding: FragmentCategoriesListBinding? = null
    private val binding
        get() = _binding
            ?: throw IllegalStateException("FragmentCategoriesListBinding must not be null")
    private val viewModel by viewModels<CategoryViewModel>()
    private val categoryRVAdapter by lazy { CategoryListRVAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCategoriesListBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecycler()
        initUi()
    }

    private fun initUi() {
        viewModel.categoryStateLD.observe(viewLifecycleOwner) { categoryState ->
            categoryRVAdapter.updateCategoryList(categoryState.categoryList)
        }
    }

    private fun initRecycler() {
        categoryRVAdapter.setOnItemClickListener(object :
            CategoryListRVAdapter.OnItemClickListener {
            override fun onItemClick(categoryId: Int) {
                val bundle = bundleOf(
                    ARG_CATEGORY_ID to categoryId
                )
                openRecipesByCategoryId(bundle)
            }
        })
        binding.rvCategory.adapter = categoryRVAdapter
    }

    private fun openRecipesByCategoryId(args: Bundle) {
        parentFragmentManager.commit {
            setReorderingAllowed(true)
            add<RecipesListFragment>(R.id.mainContainer, args = args)
            addToBackStack(null)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}