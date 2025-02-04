package aps.fithom.startandroidapp.ui.category_list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import aps.fithom.startandroidapp.R
import aps.fithom.startandroidapp.data.local.getDrawableOrNullFromAssetsByPath
import aps.fithom.startandroidapp.data.local.getFullImgPathByImgName
import aps.fithom.startandroidapp.databinding.ItemCategoryBinding
import aps.fithom.startandroidapp.domain.models.Category
import com.bumptech.glide.Glide

class CategoryListRVAdapter() :
    RecyclerView.Adapter<CategoryListRVAdapter.CategoryListViewHolder>() {

    private var categoryList: List<Category> = emptyList()
    var itemClickListener: OnItemClickListener? = null

    interface OnItemClickListener {
        fun onItemClick(category: Category)
    }

    fun updateCategoryList(categorys: List<Category>) {
        categoryList = categorys
        notifyDataSetChanged()
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        itemClickListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryListViewHolder {
        val binding =
            ItemCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CategoryListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CategoryListViewHolder, position: Int) {
        val item = categoryList[position]
        holder.tvCategoryName.text = item.title
        holder.tvCategoryDescription.text = item.description
        Glide.with(holder.itemView.context)
            .load(getFullImgPathByImgName(item.imageUrl))
            .placeholder(R.drawable.img_placeholder)
            .error(R.drawable.img_error)
            .into(holder.ivCategoryLogo)
        holder.itemView.setOnClickListener {
            itemClickListener?.onItemClick(item)
        }
    }

    override fun getItemCount(): Int {
        return categoryList.size
    }

    class CategoryListViewHolder(binding: ItemCategoryBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val ivCategoryLogo = binding.ivCategoryLogo
        val tvCategoryName = binding.tvCategoryName
        val tvCategoryDescription = binding.tvCategoryDescription
    }

}