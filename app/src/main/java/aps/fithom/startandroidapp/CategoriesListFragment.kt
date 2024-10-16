package aps.fithom.startandroidapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import aps.fithom.startandroidapp.databinding.FragmentCategoriesListBinding

class CategoriesListFragment : Fragment() {

    private var _binding:FragmentCategoriesListBinding? = null
    private val binding
        get() = _binding?:throw IllegalStateException("FragmentCategoriesListBinding must not be null")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCategoriesListBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}