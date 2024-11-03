package aps.fithom.startandroidapp.ui.recipe

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import aps.fithom.startandroidapp.data.local.Ingredient
import aps.fithom.startandroidapp.databinding.ItemIngredientBinding

class IngredientListRVAdapter(private val ingredientList: List<Ingredient>) :
    RecyclerView.Adapter<IngredientListRVAdapter.IngredientListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IngredientListViewHolder {
        val binding =
            ItemIngredientBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return IngredientListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: IngredientListViewHolder, position: Int) {
        val item = ingredientList[position]
        with(holder) {
            tvIngredientName.text = item.description
            tvIngredientAmount.text = "${item.quantity} ${item.unitOfMeasure}"
        }
    }

    override fun getItemCount(): Int {
        return ingredientList.size
    }

    class IngredientListViewHolder(binding: ItemIngredientBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val tvIngredientName = binding.tvIngredientName
        val tvIngredientAmount = binding.tvIngredientAmount
    }

}