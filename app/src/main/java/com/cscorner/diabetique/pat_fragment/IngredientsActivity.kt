package com.cscorner.diabetique.pat_fragment

import android.content.ContentValues.TAG
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.cscorner.diabetique.R
import com.cscorner.diabetique.adapter.IngredientsAdapter
import com.cscorner.diabetique.apis.APIClient
import com.cscorner.diabetique.models.RecipeModel
import com.cscorner.diabetique.response.SearchRecipes
import com.cscorner.diabetique.utils.APICredentials
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class IngredientsActivity : AppCompatActivity() {
    private lateinit var ingredientsAdapter: IngredientsAdapter
    private lateinit var recipeImg: ImageView
    private lateinit var ingredientsInstructionsRecyclerView: RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ingredients)
        recipeImg = findViewById(R.id.recipeImg)
        ingredientsInstructionsRecyclerView = findViewById(R.id.ingredientsInstructionsRecyclerView)
        val imageUrl = intent.getStringExtra("RECIPE_IMAGE_URL")
        val recipeIngredients = intent.getStringArrayExtra("RECIPE_INGREDIENTS")
        val recipeCautions = intent.getStringArrayExtra("RECIPE_CAUTIONS")
        if (imageUrl != null && recipeIngredients != null && recipeCautions != null) {
            // Afficher l'image avec Glide
            Glide.with(this)
                .load(imageUrl)
                .into(recipeImg)

            // Créer une liste combinée d'ingrédients et d'instructions
            val detailsList = mutableListOf<Any>()
            detailsList.addAll(recipeIngredients)
            detailsList.addAll(recipeCautions)

            val layoutManager = LinearLayoutManager(this)
            ingredientsInstructionsRecyclerView.layoutManager = layoutManager

            // Créer et attacher l'adaptateur avec la liste combinée
            val detailsAdapter = IngredientsAdapter(detailsList)
            ingredientsInstructionsRecyclerView.adapter = detailsAdapter
        } else {
            // Gérer le cas où les extras sont nuls
            Log.e(TAG, "Extras are null")
        }
    }

    }




