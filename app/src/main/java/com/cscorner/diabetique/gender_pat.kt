package com.cscorner.diabetique

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import androidx.lifecycle.ViewModelProvider

class gender_pat : AppCompatActivity() {
    private var viewModel: Patient = Patient()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gender_pat)

        val imageViewMale: ImageView = findViewById(R.id.Male)
        val imageViewFemale: ImageView = findViewById(R.id.Female)
        viewModel = ViewModelProvider(this).get(Patient::class.java)

        imageViewMale.setOnClickListener {
            viewModel.savePage2Data("Homme")
            startNextActivity()
        }

        // Ajoutez un écouteur de clic à l'image féminine
        imageViewFemale.setOnClickListener {
            viewModel.savePage2Data("Femme")
            startNextActivity()
        }
    }
    private fun startNextActivity() {
        // Remplacez NextActivity::class.java par le nom de votre activité suivante
        val intent = Intent(this, age_pat::class.java)
        startActivity(intent)
    }
}