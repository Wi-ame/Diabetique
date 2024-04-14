
package com.cscorner.diabetique.adapter


import RootObjectModel
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cscorner.diabetique.R
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.cscorner.diabetique.models.RecipeModel
import com.cscorner.diabetique.models.image_model.RootimageModel
import doct_fragment.MenuFragment


class RecipeAdapter(private val context: MenuFragment, private val recipes: ArrayList<RootObjectModel> ,private val listener: RecipeClickListener) : RecyclerView.Adapter<RecipeAdapter.FoodViewHolder>() {


    interface RecipeClickListener {
        fun onRecipeClicked(
            recipeIngredients: Array<String>, recipeCautions: Array<String>,imageUrl :String
        )
    }

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
        holder.itemView.setOnClickListener {
            recipeModel?.let {
                val ingredients = it.ingredients.map { ingredient -> ingredient.text }.toTypedArray()
                val cautions= it.cautions.toTypedArray()
                listener.onRecipeClicked(ingredients, cautions,recipeModel.image)


            }
        }
    }

    inner class FoodViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val label: TextView = itemView.findViewById(R.id.text_label_value)
        private val source: TextView = itemView.findViewById(R.id.text_src_value)
        private val yield: TextView = itemView.findViewById(R.id.text_yield_value)
        private val nutrientsRecyclerView: RecyclerView = itemView.findViewById(R.id.nutrientsRecyclerView)
        private val imageView: ImageView = itemView.findViewById(R.id.recipeImg)

        fun bind(recipeModel: RecipeModel) {
            label.text = " Label \t ${recipeModel.label}"
            source.text = " Source \t ${recipeModel.source}"
            yield.text = " Yield \t ${recipeModel.yield}"

            nutrientsRecyclerView.layoutManager = LinearLayoutManager(itemView.context)
            val nutrientAdapter = NutrientAdapter(recipeModel.totalNutrients.values.toList())
            nutrientsRecyclerView.adapter = nutrientAdapter
            Glide.with(itemView.context)
                .load(recipeModel.image)
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imageView)

        }



    }




}


