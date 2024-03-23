package com.cscorner.diabetique

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import androidx.lifecycle.ViewModelProvider

class Diabete : AppCompatActivity() {
    private var viewModel: Patient = Patient()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_diabete)
        val imageViewType1: ImageView = findViewById(R.id.Type1)
        val imageViewType2: ImageView = findViewById(R.id.Type2)
        viewModel = ViewModelProvider(this).get(Patient::class.java)

        imageViewType1.setOnClickListener {
            viewModel.savePage5Data("Type 1")
            startNextActivity()
        }

        imageViewType2.setOnClickListener {
            viewModel.savePage5Data("Type 2")
            startNextActivity()
        }
    }
    private fun startNextActivity() {
        // Vous pouvez passer des données supplémentaires à l'activité suivante ici
        val intent = Intent(this, poid_pat::class.java)
        startActivity(intent)
    }
}