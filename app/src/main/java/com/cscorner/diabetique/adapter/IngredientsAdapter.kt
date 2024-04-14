package com.cscorner.diabetique.adapter


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.cscorner.diabetique.R
import com.cscorner.diabetique.models.RecipeModel

class IngredientsAdapter(private val detailsList: List<Any>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val INGREDIENT_TYPE = 1
    private val CAUTION_TYPE = 2

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            INGREDIENT_TYPE -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.ingredients_adapter_item, parent, false)
                IngredientViewHolder(view)
            }
            else -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.instructions_adapter_item, parent, false)
                InstructionViewHolder(view)
            }
        }
    }

    override fun getItemCount(): Int {
        return detailsList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = detailsList[position]
        when (holder) {
            is IngredientViewHolder -> {
                holder.bind(item as RecipeModel.Ingredient)
            }
            is InstructionViewHolder -> {
                holder.bind(item as String)
                holder.itemView.setBackgroundColor(ContextCompat.getColor(holder.itemView.context, R.color.lavender1))

            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (detailsList[position] is RecipeModel.Ingredient) {
            INGREDIENT_TYPE
        } else {
            CAUTION_TYPE
        }
    }

    inner class IngredientViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val nameTextView: TextView = itemView.findViewById(R.id.ingredientNameTextView)
        private val quantityTextView: TextView = itemView.findViewById(R.id.ingredientQuantityTextView)
        private val measureTextView: TextView = itemView.findViewById(R.id.ingredientMeasureTextView)
        private val foodTextView: TextView = itemView.findViewById(R.id.ingredientFoodTextView)
        private val weightTextView: TextView = itemView.findViewById(R.id.ingredientWeightTextView)
        private val foodIdTextView: TextView = itemView.findViewById(R.id.ingredientFoodIdTextView)

        fun bind(ingredient: RecipeModel.Ingredient) {
            nameTextView.text = ingredient.text
            quantityTextView.text = ingredient.quantity.toString()
            measureTextView.text = ingredient.measure
            foodTextView.text = ingredient.food
            weightTextView.text = ingredient.weight.toString()
            foodIdTextView.text = ingredient.foodId
        }
    }

    inner class InstructionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val instructionTextView: TextView = itemView.findViewById(R.id.instructionTextView)

        fun bind(instruction: String) {
            instructionTextView.text = instruction

        }
    }
}
