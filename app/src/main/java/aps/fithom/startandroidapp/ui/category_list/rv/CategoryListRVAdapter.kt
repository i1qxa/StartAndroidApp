package aps.fithom.startandroidapp.ui.category_list.rv

import android.graphics.drawable.Drawable
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import aps.fithom.startandroidapp.R
import aps.fithom.startandroidapp.data.local.Category
import java.io.InputStream

class CategoryListRVAdapter(private val categoryList:List<Category>):RecyclerView.Adapter<CategoryListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryListViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_category, parent, false)
        return CategoryListViewHolder(view)
    }

    override fun onBindViewHolder(holder: CategoryListViewHolder, position: Int) {
        val item = categoryList[position]
        holder.tvCategoryName.text = item.title
        holder.tvCategoryDescription.text = item.description
        try {
            val inputStream: InputStream? = holder.itemView.context?.assets?.open(categoryList[position].imageUrl)
            val drawable = Drawable.createFromStream(inputStream, null)
            holder.ivCategoryLogo.setImageDrawable(drawable)
        }catch (e:Exception){
            holder.ivCategoryLogo.setImageDrawable(null)
            Log.d("!!!", "Error loading img: ${e.message}")
        }
    }

    override fun getItemCount(): Int {
        return categoryList.size
    }
}