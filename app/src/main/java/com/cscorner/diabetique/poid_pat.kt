package com.cscorner.diabetique

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider

class poid_pat : AppCompatActivity() {
    private lateinit var fullName: String
    private lateinit var email: String
    private lateinit var password: String
    private lateinit var confirmPassword: String
    private lateinit var phoneNumber: String
    private lateinit var doctor: String
    private lateinit var gender: String
    private lateinit var age: String
    private lateinit var diabete :String
    private lateinit var resultTextView: TextView
    private lateinit var weightEditText: EditText
    private lateinit var heightEditText: EditText
    private fun createIntentWithPoids(poids: String, taille: String): Intent {
        val intent = Intent(this, physqiue::class.java)
        intent.putExtras(intent.extras ?: Bundle())
        intent.putExtra("poids",poids)
        intent.putExtra("taille",taille)
        return intent
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_poid_pat)
        weightEditText = findViewById(R.id.editTextWeight)
        heightEditText = findViewById(R.id.editTextHeight)
        resultTextView = findViewById(R.id.textViewResult)
        fullName = intent.getStringExtra("fullName") ?: ""
        email = intent.getStringExtra("email") ?: ""
        password = intent.getStringExtra("password") ?: ""
        confirmPassword = intent.getStringExtra("confirmPassword") ?: ""
        phoneNumber = intent.getStringExtra("phoneNumber") ?: ""
        doctor = intent.getStringExtra("doctor") ?: ""
        gender =intent.getStringExtra("gender") ?: ""
        age = intent.getStringExtra("age")?: ""
        diabete =intent.getStringExtra("diabete")?: ""

        val buttonImage: ImageButton = findViewById(R.id.button)

        buttonImage.setOnClickListener {
            if (validateInput()) {
                val poids: String = weightEditText.text.toString()
                val taille: String = heightEditText.text.toString()
                val intent = createIntentWithPoids(poids,taille).apply{
                    putExtra("fullName", fullName)
                    putExtra("email", email)
                    putExtra("password", password)
                    putExtra("passwordconf", confirmPassword)
                    putExtra("phoneNumber", phoneNumber)
                    putExtra("doctor", doctor)
                    putExtra("gender", gender)
                    putExtra("age", age)
                    putExtra("diabete",diabete)
                }
                startNextActivity(intent)
            }
        }




        val submitButton: Button = findViewById(R.id.buttonSubmit)
        submitButton.setOnClickListener { onSubmitClicked(it) }
    }

    private fun onSubmitClicked(view: View) {
        val weightInput: String = weightEditText.text.toString().trim()
        val heightInput: String = heightEditText.text.toString().trim()

        if (TextUtils.isEmpty(weightInput) || TextUtils.isEmpty(heightInput)) {
            resultTextView.text = "Veuillez remplir tous les champs."
            return
        }

        val weight: Double
        val height: Double

        try {
            weight = weightInput.toDouble()
            height = heightInput.toDouble()
        } catch (e: NumberFormatException) {
            resultTextView.text = "Veuillez entrer des valeurs valides pour le poids et la taille."
            return
        }

        if (weight <= 0 || height <= 0) {
            resultTextView.text = "Veuillez entrer des valeurs positives pour le poids et la taille."
            return
        }

        val bmi = calculateBMI(weight, height)

        // Comparez l'IMC et donnez un message en conséquence
        if (bmi < 18.5) {
            resultTextView.text = "IMC: $bmi - Poids insuffisant par rapport à la taille."
        } else if (bmi >= 18.5 && bmi < 25) {
            resultTextView.text = "IMC: $bmi - Poids normal par rapport à la taille."
        } else {
            resultTextView.text = "IMC: $bmi - Surpoids par rapport à la taille."
        }
    }
    private fun calculateBMI(weight: Double, height: Double): Double {
        // Formule de l'IMC : poids (kg) / (taille (m) * taille (m))
        return weight / ((height / 100) * (height / 100))
    }

    private fun validateInput(): Boolean {
        // Vérifie si les champs de poids et de taille sont vides
        return if (TextUtils.isEmpty(weightEditText.text.toString()) || TextUtils.isEmpty(heightEditText.text.toString())) {
            // Affichage d'un message d'erreur si les champs sont vides
            Toast.makeText(this, "Veuillez remplir tous les champs", Toast.LENGTH_SHORT).show()
            false
        } else {
            true
        }
    }
    private fun startNextActivity(intent: Intent) {
        startActivity(intent)
    }
}