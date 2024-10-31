package aps.fithom.startandroidapp.ui.recipes_list

import android.graphics.drawable.Drawable
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import aps.fithom.startandroidapp.R
import aps.fithom.startandroidapp.data.local.Recipe
import aps.fithom.startandroidapp.databinding.ItemRecipeBinding
import java.io.InputStream

class RecipeListRVAdapter(private val recipeList: List<Recipe>) :
    RecyclerView.Adapter<RecipeListRVAdapter.RecipeListViewHolder>() {

    var recipeItemClickListener: OnRecipeItemClickListener? = null

    interface OnRecipeItemClickListener {
        fun onItemClick(recipeId: Int)
    }

    fun setOnRecipeItemClickListener(listener: OnRecipeItemClickListener) {
        recipeItemClickListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeListViewHolder {
        val binding = ItemRecipeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RecipeListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecipeListViewHolder, position: Int) {
        val item = recipeList[position]
        holder.tvRecipeName.text = item.title
        try {
            val inputStream: InputStream? =
                holder.itemView.context?.assets?.open(recipeList[position].imageUrl)
            val drawable = Drawable.createFromStream(inputStream, null)
            holder.ivRecipeLogo.setImageDrawable(drawable)
            holder.ivRecipeLogo.contentDescription =
                holder.itemView.context.getString(
                    R.string.content_description_recipe_img,
                    item.title
                )
        } catch (e: Exception) {
            holder.ivRecipeLogo.setImageDrawable(null)
            Log.d("!!!", "Error loading img: ${e.message}")
        }
        holder.itemView.setOnClickListener {
            recipeItemClickListener?.onItemClick(item.id)
        }
    }

    override fun getItemCount(): Int {
        return recipeList.size
    }

    class RecipeListViewHolder(binding: ItemRecipeBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val ivRecipeLogo = binding.ivRecipeLogo
        val tvRecipeName = binding.tvRecipeName
    }

}