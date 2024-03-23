package com.cscorner.diabetique

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.lifecycle.ViewModelProvider

class physqiue : AppCompatActivity() {
    private var viewModel: Patient = Patient()
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_physqiue)
        // Récupérez les boutons d'activité physique
        val buttonNon: Button = findViewById(R.id.Non)
        val buttonPromenades: Button = findViewById(R.id.Des_Promenades)
        val button1A2Fois: Button = findViewById(R.id.Une_a_2fois_parsemaine)
        val button3A5Fois: Button = findViewById(R.id.trois_a_5fois_parsemaine)
        viewModel = ViewModelProvider(this).get(Patient::class.java)

        // Ajoutez un gestionnaire de clic pour chaque bouton
        buttonNon.setOnClickListener {
            viewModel.savePage7Data("Non")
            // Appel de la fonction pour passer à la page suivante
            goToNextPage()
        }

        buttonPromenades.setOnClickListener {
            viewModel.savePage7Data("Promenades")
            // Appel de la fonction pour passer à la page suivante
            goToNextPage()
        }

        button1A2Fois.setOnClickListener {
            viewModel.savePage7Data("une à 2 fois par semaine")
            // Appel de la fonction pour passer à la page suivante
            goToNextPage()
        }
        button3A5Fois.setOnClickListener {
            viewModel.savePage7Data("3 à 5 fois par semaines")
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