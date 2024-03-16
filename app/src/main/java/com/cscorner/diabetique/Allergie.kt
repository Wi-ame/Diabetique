package com.cscorner.diabetique

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import androidx.appcompat.app.AppCompatActivity

class Allergie : AppCompatActivity() {
    private lateinit var radioButtonOui: RadioButton
    private lateinit var radioButtonNon: RadioButton
    private lateinit var editTextAllergie: EditText
    private lateinit var buttonNext: Button
    private var isOuiSelected = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_allergie)
        radioButtonOui = findViewById(R.id.radioButtonOui)
        radioButtonNon = findViewById(R.id.radioButtonNon)
        editTextAllergie = findViewById(R.id.editTextAllergie)
        buttonNext = findViewById(R.id.buttonNext)

        // Rendre le EditText invisible au démarrage
        editTextAllergie.visibility = View.GONE

        // Ajoutez un OnClickListener au bouton "Non"
        radioButtonNon.setOnClickListener {
            isOuiSelected = false
            // Rendre le EditText invisible
            editTextAllergie.visibility = View.GONE
            // Rendre le bouton "Next" visible
            buttonNext.visibility = View.VISIBLE
        }

        // Ajoutez un OnClickListener au bouton "Oui"
        radioButtonOui.setOnClickListener {
            isOuiSelected = true
            // Rendre le EditText visible
            editTextAllergie.visibility = View.VISIBLE
            // Rendre le bouton "Next" invisible (car vous ne voulez pas passer à l'activité suivante immédiatement)
            buttonNext.visibility = View.VISIBLE
        }

        // Ajoutez un OnClickListener au bouton "Next"
        buttonNext.setOnClickListener {
            // Si "Oui" est sélectionné, vérifiez si l'EditText est rempli avant de passer à l'activité suivante
            if (isOuiSelected && editTextAllergie.text.toString().isEmpty()) {
                // Affichez un message ou prenez toute autre action si l'EditText n'est pas rempli
            } else {
                // Créez un Intent pour démarrer l'activité suivante
                val intent = Intent(this, Pat_Auth::class.java)
                startActivity(intent)
            }
        }
    }
}