
package com.cscorner.diabetique.adapter


import RootObjectModel
import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import com.cscorner.diabetique.R
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.cscorner.diabetique.models.RecipeModel
import doct_fragment.MenuFragment


class RecipeAdapter(private val context: MenuFragment, private val recipes: ArrayList<RootObjectModel>) : RecyclerView.Adapter<RecipeAdapter.FoodViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.custom_entites, parent, false)
        return FoodViewHolder(view)
    }

    override fun getItemCount(): Int {
        return recipes.size
    }

    override fun onBindViewHolder(holder: FoodViewHolder, position: Int) {
        val recipeModel = recipes[position].recipeModel
        if (recipeModel != null) {
            holder.bind(recipeModel)
        }
    }

    inner class FoodViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val label: TextView = itemView.findViewById(R.id.text_label_value)
        private val source: TextView = itemView.findViewById(R.id.text_src_value)
        private val yield: TextView = itemView.findViewById(R.id.text_yield_value)
        private val calories: TextView = itemView.findViewById(R.id.text_calories_value)
        private val totalweight: TextView = itemView.findViewById(R.id.text_weight_value)
        private val imageView: ImageView = itemView.findViewById(R.id.recipeImg)

        fun bind(recipeModel: RecipeModel) {
            label.text = " Label \t ${recipeModel.label}"
            source.text = " Source \t ${recipeModel.source}"
            yield.text = " Yield \t ${recipeModel.yield}"
            calories.text = " Calories \t ${recipeModel.calories}"
            totalweight.text = " Total Weight \t ${recipeModel.totalWeights}"
            Glide.with(itemView.context)
                .load(recipeModel.image)
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imageView)
        }
    }
}
