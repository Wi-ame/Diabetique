package com.cscorner.diabetique

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText

class Information : AppCompatActivity() {
    private lateinit var editTextFullName: EditText
    private lateinit var editTextEmail: EditText
    private lateinit var editTextPassword: EditText
    private lateinit var editTextConfirmPassword: EditText
    private lateinit var editTextPhoneNumber: EditText
    private lateinit var editTextDoctor: EditText
    private lateinit var buttonNext: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_information)
        editTextFullName = findViewById(R.id.editTextFullName)
        editTextEmail = findViewById(R.id.editTextEmail)
        editTextPassword = findViewById(R.id.editTextPassword)
        editTextConfirmPassword = findViewById(R.id.editTextConfirmPassword)
        editTextPhoneNumber = findViewById(R.id.editTextPhoneNumber)
        editTextDoctor = findViewById(R.id.editTextDoctor)
        buttonNext = findViewById(R.id.buttonNext)
        buttonNext.setOnClickListener {
            // Vérifiez ici si les informations sont valides avant de passer à l'étape suivante
            // Pour l'exemple, simplement démarrer l'activité suivante
            startSexActivity()
        }
    }
    private fun startSexActivity() {
        val intent = Intent(this, gender_pat::class.java)
        startActivity(intent)
    }
}