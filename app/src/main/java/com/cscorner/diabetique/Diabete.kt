package com.cscorner.diabetique

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView

class Diabete : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_diabete)
        val imageViewType1: ImageView = findViewById(R.id.imagetype1)
        val imageViewType2: ImageView = findViewById(R.id.imagetype2)

        imageViewType1.setOnClickListener {
            startNextActivity()
        }

        imageViewType2.setOnClickListener {
            startNextActivity()
        }
    }
    private fun startNextActivity() {
        // Vous pouvez passer des données supplémentaires à l'activité suivante ici
        val intent = Intent(this, poid_pat::class.java)
        startActivity(intent)
    }
}