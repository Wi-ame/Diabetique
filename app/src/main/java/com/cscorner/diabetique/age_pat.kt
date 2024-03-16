package com.cscorner.diabetique

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView

class age_pat : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_age_pat)

        val imageViewYoung: ImageView = findViewById(R.id.imageView0_8)
        val imageViewMature: ImageView = findViewById(R.id.imageView8_18)
        val imageViewAvant: ImageView = findViewById(R.id.imageView18_33)
        val imageViewLast: ImageView = findViewById(R.id.imageView33_55)

        imageViewYoung.setOnClickListener {
            startNextActivity()
        }

        imageViewMature.setOnClickListener {
            startNextActivity()
        }

        imageViewAvant.setOnClickListener {
            startNextActivity()
        }

        imageViewLast.setOnClickListener {
            startNextActivity()
        }
    }

    private fun startNextActivity() {
        // Remplacez NextActivity::class.java par le nom de votre activité suivante
        val intent = Intent(this, Diabete::class.java)
        startActivity(intent)
    }
}