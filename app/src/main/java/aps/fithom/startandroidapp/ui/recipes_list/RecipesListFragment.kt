package aps.fithom.startandroidapp.ui.recipes_list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import aps.fithom.startandroidapp.databinding.FragmentRecipesListBinding
import aps.fithom.startandroidapp.ui.category_list.ARG_CATEGORY_ID
import aps.fithom.startandroidapp.ui.category_list.ARG_CATEGORY_IMAGE_URL
import aps.fithom.startandroidapp.ui.category_list.ARG_CATEGORY_NAME

class RecipesListFragment : Fragment() {

    private var _binding: FragmentRecipesListBinding? = null
    private val binding: FragmentRecipesListBinding
        get() = _binding
            ?: throw (IllegalStateException("FragmentRecipesListBinding must not be null"))
    private var categoryId:Int? = null
    private var categoryName:String? = null
    private var categoryImageUrl:String? = null


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
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}