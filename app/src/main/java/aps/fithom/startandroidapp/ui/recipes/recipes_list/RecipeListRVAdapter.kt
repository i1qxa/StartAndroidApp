package aps.fithom.startandroidapp.ui.recipes.recipes_list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import aps.fithom.startandroidapp.R
import aps.fithom.startandroidapp.data.local.getDrawableOrNullFromAssetsByPath
import aps.fithom.startandroidapp.databinding.ItemRecipeBinding
import aps.fithom.startandroidapp.domain.models.Recipe

class RecipeListRVAdapter() :
    RecyclerView.Adapter<RecipeListRVAdapter.RecipeListViewHolder>() {

    private var recipesList: List<Recipe> = emptyList()
    var recipeItemClickListener: OnRecipeItemClickListener? = null

    fun updateRecipesList(recipes: List<Recipe>) {
        recipesList = recipes
        notifyDataSetChanged()
    }

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
        val item = recipesList[position]
        holder.tvRecipeName.text = item.title
        holder.itemView.context?.getDrawableOrNullFromAssetsByPath(item.imageUrl)?.let { drawable ->
            holder.ivRecipeLogo.setImageDrawable(drawable)
            holder.ivRecipeLogo.contentDescription =
                holder.itemView.context.getString(
                    R.string.content_description_recipe_img,
                    item.title
                )
        }
        holder.itemView.setOnClickListener {
            recipeItemClickListener?.onItemClick(item.id)
        }
    }

    override fun getItemCount(): Int {
        return recipesList.size
    }

    class RecipeListViewHolder(binding: ItemRecipeBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val ivRecipeLogo = binding.ivRecipeLogo
        val tvRecipeName = binding.tvRecipeName
    }

}