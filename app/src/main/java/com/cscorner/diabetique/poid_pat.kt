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

class poid_pat : AppCompatActivity() {
    private lateinit var resultTextView: TextView
    private lateinit var weightEditText: EditText
    private lateinit var heightEditText: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_poid_pat)
        weightEditText = findViewById(R.id.editTextWeight)
        heightEditText = findViewById(R.id.editTextHeight)
        resultTextView = findViewById(R.id.textViewResult) // Ajout de cette ligne

        val buttonImage: ImageButton = findViewById(R.id.button)

        buttonImage.setOnClickListener {
            // Appel de la fonction pour passer à la page suivante
            goToNextPage()
        }

        val submitButton: Button = findViewById(R.id.buttonSubmit)
        submitButton.setOnClickListener { onSubmitClicked(it) }
    }
    private fun goToNextPage() {
        // Intent pour démarrer l'activité suivante
        val intent = Intent(this, physqiue::class.java)
        startActivity(intent)
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

}