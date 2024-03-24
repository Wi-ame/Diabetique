package com.cscorner.diabetique

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.lifecycle.ViewModelProvider

class physqiue : AppCompatActivity() {
    private lateinit var fullName: String
    private lateinit var email: String
    private lateinit var password: String
    private lateinit var confirmPassword: String
    private lateinit var phoneNumber: String
    private lateinit var doctor: String
    private lateinit var gender: String
    private lateinit var age: String
    private lateinit var diabete :String
    private lateinit var poids: String
    private lateinit var taille :String
    private fun createIntentWithPhysique(activite: String): Intent {
        val intent = Intent(this, Allergie::class.java)
        intent.putExtras(intent.extras ?: Bundle())
        intent.putExtra("activite", activite)
        return intent
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_physqiue)
        fullName = intent.getStringExtra("fullName") ?: ""
        email = intent.getStringExtra("email") ?: ""
        password = intent.getStringExtra("password") ?: ""
        confirmPassword = intent.getStringExtra("passwordconf") ?: ""
        phoneNumber = intent.getStringExtra("phoneNumber") ?: ""
        doctor = intent.getStringExtra("doctor") ?: ""
        gender =intent.getStringExtra("gender") ?: ""
        age = intent.getStringExtra("age")?: ""
        diabete =intent.getStringExtra("diabete")?: ""
        poids =intent.getStringExtra("poids")?: ""
        taille =intent.getStringExtra("taille")?: ""
        // Récupérez les boutons d'activité physique
        val buttonNon: Button = findViewById(R.id.Non)
        val buttonPromenades: Button = findViewById(R.id.Des_Promenades)
        val button1A2Fois: Button = findViewById(R.id.Une_a_2fois_parsemaine)
        val button3A5Fois: Button = findViewById(R.id.trois_a_5fois_parsemaine)

        // Ajoutez un gestionnaire de clic pour chaque bouton
        buttonNon.setOnClickListener {
            val intent = createIntentWithPhysique("Non").apply {
                putExtra("fullName", fullName)
                putExtra("email", email)
                putExtra("password", password)
                putExtra("passwordconf", confirmPassword)
                putExtra("phoneNumber", phoneNumber)
                putExtra("doctor", doctor)
                putExtra("gender", gender)
                putExtra("age", age)
                putExtra("diabete",diabete)
                putExtra("poids",poids)
                putExtra("taille",taille)
            }
            startNextActivity(intent)

        }

        buttonPromenades.setOnClickListener {
            val intent = createIntentWithPhysique("Des Promenades").apply {
                putExtra("fullName", fullName)
                putExtra("email", email)
                putExtra("password", password)
                putExtra("passwordconf", confirmPassword)
                putExtra("phoneNumber", phoneNumber)
                putExtra("doctor", doctor)
                putExtra("gender", gender)
                putExtra("age", age)
                putExtra("diabete",diabete)
                putExtra("poids",poids)
                putExtra("taille",taille)
            }
            startNextActivity(intent)


        }

        button1A2Fois.setOnClickListener {
            val intent = createIntentWithPhysique("2 à 3 fois par semaine").apply {
                putExtra("fullName", fullName)
                putExtra("email", email)
                putExtra("password", password)
                putExtra("passwordconf", confirmPassword)
                putExtra("phoneNumber", phoneNumber)
                putExtra("doctor", doctor)
                putExtra("gender", gender)
                putExtra("age", age)
                putExtra("diabete",diabete)
                putExtra("poids",poids)
                putExtra("taille",taille)
            }
            startNextActivity(intent)

        }
        button3A5Fois.setOnClickListener {
            val intent = createIntentWithPhysique("3 à 5 fois par semaine").apply {
                putExtra("fullName", fullName)
                putExtra("email", email)
                putExtra("password", password)
                putExtra("passwordconf", confirmPassword)
                putExtra("phoneNumber", phoneNumber)
                putExtra("doctor", doctor)
                putExtra("gender", gender)
                putExtra("age", age)
                putExtra("diabete",diabete)
                putExtra("poids",poids)
                putExtra("taille",taille)
            }
            startNextActivity(intent)

        }
    }
    private fun startNextActivity(intent: Intent) {
        startActivity(intent)
    }

}