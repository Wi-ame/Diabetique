package com.cscorner.diabetique.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.cscorner.diabetique.R
import com.cscorner.diabetique.models.RecipeModel

class NutrientAdapter(private val nutrients: List<RecipeModel.Nutrient>) : RecyclerView.Adapter<NutrientAdapter.NutrientViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NutrientViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_nutrient, parent, false)
        return NutrientViewHolder(view)
    }

    override fun getItemCount(): Int {
        return nutrients.size
    }

    override fun onBindViewHolder(holder: NutrientViewHolder, position: Int) {
        holder.bind(nutrients[position])
    }

    inner class NutrientViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val label: TextView = itemView.findViewById(R.id.text_nutrient_label)
        private val quantity: TextView = itemView.findViewById(R.id.text_nutrient_quantity)
        private val unit: TextView = itemView.findViewById(R.id.text_nutrient_unit)
        fun bind(nutrient: RecipeModel.Nutrient) {
            label.text = nutrient.label
            quantity.text = nutrient.quantity.toString()
            unit.text = nutrient.unit
        }
    }
}