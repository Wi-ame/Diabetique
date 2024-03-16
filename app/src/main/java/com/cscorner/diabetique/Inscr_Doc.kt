package com.cscorner.diabetique

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class Inscr_Doc : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inscr_doc)
        val buttonNext: Button = findViewById(R.id.buttonNext)

        // Ajoutez un gestionnaire de clic au bouton "Next"
        buttonNext.setOnClickListener {
            // Créez un Intent pour démarrer l'activité d'authentification
            val intent = Intent(this, Doctor_Auth::class.java)
            startActivity(intent)
        }
    }
}