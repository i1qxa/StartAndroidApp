package aps.fithom.startandroidapp.ui.recipes.recipe

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import aps.fithom.startandroidapp.domain.models.Ingredient
import aps.fithom.startandroidapp.databinding.ItemIngredientBinding
import java.math.BigDecimal
import java.math.RoundingMode

class IngredientListRVAdapter(private val ingredientList: List<Ingredient>) :
    RecyclerView.Adapter<IngredientListRVAdapter.IngredientListViewHolder>() {

    private var quantity = 1

    fun updateIngredients(progress: Int) {
        quantity = progress
        this.notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IngredientListViewHolder {
        val binding =
            ItemIngredientBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return IngredientListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: IngredientListViewHolder, position: Int) {
        val item = ingredientList[position]
        item.quantity.toDoubleOrNull()?.let {
            val totalQuantity = BigDecimal(it) * BigDecimal(quantity)
            val displayQuantity = totalQuantity
                .setScale(1, RoundingMode.HALF_UP)
                .stripTrailingZeros()
                .toPlainString()
            with(holder) {
                tvIngredientName.text = item.description
                tvIngredientAmount.text = "$displayQuantity ${item.unitOfMeasure}"
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