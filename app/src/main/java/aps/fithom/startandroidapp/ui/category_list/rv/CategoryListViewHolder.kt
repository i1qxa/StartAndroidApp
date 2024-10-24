package aps.fithom.startandroidapp.ui.category_list.rv

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import aps.fithom.startandroidapp.databinding.ItemCategoryBinding

class CategoryListViewHolder(itemView:View):RecyclerView.ViewHolder(itemView) {
    private val binding = ItemCategoryBinding.bind(itemView)
    val ivCategoryLogo = binding.ivCategoryLogo
    val tvCategoryName = binding.tvCategoryName
    val tvCategoryDescription = binding.tvCategoryDescription
}