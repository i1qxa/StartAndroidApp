package aps.fithom.startandroidapp.ui.category_list

import android.graphics.drawable.Drawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import aps.fithom.startandroidapp.R
import aps.fithom.startandroidapp.data.local.Category
import aps.fithom.startandroidapp.databinding.ItemCategoryBinding
import java.io.InputStream

class CategoryListRVAdapter(private val categoryList: List<Category>) :
    RecyclerView.Adapter<CategoryListRVAdapter.CategoryListViewHolder>() {

    var itemClickListener: OnItemClickListener? = null

    interface OnItemClickListener {
        fun onItemClick() {
        }
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        itemClickListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryListViewHolder {
        val binding = ItemCategoryBinding.inflate(LayoutInflater.from(parent.context))
        return CategoryListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CategoryListViewHolder, position: Int) {
        val item = categoryList[position]
        holder.tvCategoryName.text = item.title
        holder.tvCategoryDescription.text = item.description
        try {
            val inputStream: InputStream? =
                holder.itemView.context?.assets?.open(categoryList[position].imageUrl)
            val drawable = Drawable.createFromStream(inputStream, null)
            holder.ivCategoryLogo.setImageDrawable(drawable)
            holder.ivCategoryLogo.contentDescription =
                holder.itemView.context.getString(
                    R.string.category_image_content_description,
                    item.title
                )
        } catch (e: Exception) {
            holder.ivCategoryLogo.setImageDrawable(null)
            Log.d("!!!", "Error loading img: ${e.message}")
        }
        holder.itemView.setOnClickListener {
            itemClickListener?.onItemClick()
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