package aps.fithom.startandroidapp.ui.category_list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import aps.fithom.startandroidapp.R
import aps.fithom.startandroidapp.databinding.FragmentCategoriesListBinding
import aps.fithom.startandroidapp.domain.models.Category

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
            if (categoryState.categoryList == null) {
                Toast.makeText(
                    requireContext(),
                    getString(R.string.error_loading_category_list),
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                categoryRVAdapter.updateCategoryList(categoryState.categoryList)
            }
        }
    }

    private fun initRecycler() {
        categoryRVAdapter.setOnItemClickListener(object :
            CategoryListRVAdapter.OnItemClickListener {
            override fun onItemClick(category: Category) {
                findNavController().navigate(
                    CategoriesListFragmentDirections.actionCategoriesListFragmentToRecipesListFragment(
                        category
                    )
                )
            }
        })
        binding.rvCategory.adapter = categoryRVAdapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}