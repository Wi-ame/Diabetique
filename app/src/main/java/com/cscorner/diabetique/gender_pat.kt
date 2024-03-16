package com.cscorner.diabetique

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView

class gender_pat : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gender_pat)
        val imageViewMale: ImageView = findViewById(R.id.imageViewMale)
        val imageViewFemale: ImageView = findViewById(R.id.imageViewFemale)

        imageViewMale.setOnClickListener {
            startNextActivity()
        }

        // Ajoutez un écouteur de clic à l'image féminine
        imageViewFemale.setOnClickListener {
            startNextActivity()
        }
    }
    private fun startNextActivity() {
        // Remplacez NextActivity::class.java par le nom de votre activité suivante
        val intent = Intent(this, age_pat::class.java)
        startActivity(intent)
    }
}