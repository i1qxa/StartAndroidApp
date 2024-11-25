package aps.fithom.startandroidapp.ui.recipes.recipe

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import aps.fithom.startandroidapp.databinding.ItemMethodBinding

class CookingMethodListRVAdapter() :
    RecyclerView.Adapter<CookingMethodListRVAdapter.CookingMethodListViewHolder>() {

    private var cookingMethodList = listOf<String>()

    fun updateCookingMethodList(methodList: List<String>) {
        cookingMethodList = methodList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CookingMethodListViewHolder {
        val binding = ItemMethodBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CookingMethodListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CookingMethodListViewHolder, position: Int) {
        val item = cookingMethodList[position]
        holder.tvCookingMethod.text = "${(position + 1)}. $item"
    }

    override fun getItemCount(): Int {
        return cookingMethodList.size
    }

    class CookingMethodListViewHolder(binding: ItemMethodBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val tvCookingMethod = binding.tvMethod
    }

}