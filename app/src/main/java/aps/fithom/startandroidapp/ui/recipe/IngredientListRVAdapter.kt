package aps.fithom.startandroidapp.ui.recipe

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import aps.fithom.startandroidapp.data.local.Ingredient
import aps.fithom.startandroidapp.databinding.ItemIngredientBinding

class IngredientListRVAdapter(private val ingredientList: List<Ingredient>) :
    RecyclerView.Adapter<IngredientListRVAdapter.IngredientListViewHolder>() {

    private var quantity = 1

    fun updateIngredients(progress: Int) {
        quantity = progress
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IngredientListViewHolder {
        val binding =
            ItemIngredientBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return IngredientListViewHolder(binding)
    }

    @SuppressLint("DefaultLocale")
    override fun onBindViewHolder(holder: IngredientListViewHolder, position: Int) {
        val item = ingredientList[position]
        item.quantity.toDoubleOrNull()?.let {
            val quantityAsDouble = it * quantity
            val quantityPortion = if (quantityAsDouble % 1 == 0.0) {
                quantityAsDouble.toInt().toString()
            } else {
                String.format("%.1f", quantityAsDouble)
            }
            with(holder) {
                tvIngredientName.text = item.description
                tvIngredientAmount.text = "$quantityPortion ${item.unitOfMeasure}"
            }
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