package com.cscorner.diabetique

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class EspacesActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_espaces)
        val docteurButton: Button = findViewById(R.id.button)
        docteurButton.setOnClickListener {

            val intent = Intent(this, Doctor_Auth::class.java)
            startActivity(intent)
        }
        val patientButton: Button = findViewById(R.id.button2)
        patientButton.setOnClickListener {
            // Redirigez vers la page d'authentification du patient
            val intent = Intent(this, Pat_Auth::class.java)
            startActivity(intent)
        }
    }
}