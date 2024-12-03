package aps.fithom.startandroidapp.ui.category_list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import aps.fithom.startandroidapp.databinding.FragmentCategoriesListBinding
import aps.fithom.startandroidapp.domain.models.CategoryParcel

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
            override fun onItemClick(categoryParcel: CategoryParcel) {
                findNavController().navigate(
                    CategoriesListFragmentDirections.actionCategoriesListFragmentToRecipesListFragment(
                        categoryParcel
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