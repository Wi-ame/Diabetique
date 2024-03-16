package com.cscorner.diabetique

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class physqiue : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_physqiue)
        // Récupérez les boutons d'activité physique
        val buttonNon: Button = findViewById(R.id.buttonNon)
        val buttonPromenades: Button = findViewById(R.id.buttonPromenades)
        val button1A2Fois: Button = findViewById(R.id.button1A2Fois)
        val button3A5Fois: Button = findViewById(R.id.button3A5Fois)

        // Ajoutez un gestionnaire de clic pour chaque bouton
        buttonNon.setOnClickListener {
            // Appel de la fonction pour passer à la page suivante
            goToNextPage()
        }

        buttonPromenades.setOnClickListener {
            // Appel de la fonction pour passer à la page suivante
            goToNextPage()
        }

        button1A2Fois.setOnClickListener {
            // Appel de la fonction pour passer à la page suivante
            goToNextPage()
        }

        button3A5Fois.setOnClickListener {
            // Appel de la fonction pour passer à la page suivante
            goToNextPage()
        }
    }
    private fun goToNextPage() {
        // Intent pour démarrer l'activité suivante
        val intent = Intent(this, Allergie::class.java)
        startActivity(intent)
    }
}