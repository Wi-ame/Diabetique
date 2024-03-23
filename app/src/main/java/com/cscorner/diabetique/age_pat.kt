package com.cscorner.diabetique

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import androidx.lifecycle.ViewModelProvider

class age_pat : AppCompatActivity() {
    private var viewModel: Patient = Patient()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_age_pat)
        viewModel = ViewModelProvider(this).get(Patient::class.java)

        val imageViewYoung: ImageView = findViewById(R.id.Age_18_33)
        val imageViewMature: ImageView = findViewById(R.id.Age_33_44)
        val imageViewAvant: ImageView = findViewById(R.id.Age_44_55)
        val imageViewLast: ImageView = findViewById(R.id.Age_55_plus)

        imageViewYoung.setOnClickListener {

            viewModel.savePage3Data("18-33")


            startNextActivity()
        }

        imageViewMature.setOnClickListener {
            viewModel.savePage3Data("33-44")

            startNextActivity()
        }

        imageViewAvant.setOnClickListener {
            viewModel.savePage3Data("44-55")

            startNextActivity()
        }

        imageViewLast.setOnClickListener {
            viewModel.savePage3Data("55+")
            startNextActivity()
        }
    }

    private fun startNextActivity() {
        // Remplacez NextActivity::class.java par le nom de votre activité suivante
        val intent = Intent(this, Diabete::class.java)
        startActivity(intent)
    }

}