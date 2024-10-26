package aps.fithom.startandroidapp.ui.category_list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.add
import androidx.fragment.app.commit
import aps.fithom.startandroidapp.R
import aps.fithom.startandroidapp.data.local.STUB
import aps.fithom.startandroidapp.databinding.FragmentCategoriesListBinding
import aps.fithom.startandroidapp.ui.recipes_list.RecipesListFragment

class CategoriesListFragment : Fragment() {

    private var _binding: FragmentCategoriesListBinding? = null
    private val binding
        get() = _binding
            ?: throw IllegalStateException("FragmentCategoriesListBinding must not be null")

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
    }

    private fun initRecycler() {
        val recycler = binding.rvCategory
        val rvAdapter = CategoryListRVAdapter(STUB.getCategories())
        rvAdapter.setOnItemClickListener(object : CategoryListRVAdapter.OnItemClickListener {
            override fun onItemClick() {
                openRecipesByCategoryId()
            }
        })
        recycler.adapter = rvAdapter
    }

    private fun openRecipesByCategoryId() {
        parentFragmentManager.commit {
            setReorderingAllowed(true)
            add<RecipesListFragment>(R.id.mainContainer)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}