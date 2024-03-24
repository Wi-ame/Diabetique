package com.cscorner.diabetique

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.lifecycle.ViewModelProvider

class gender_pat : AppCompatActivity() {
    private lateinit var fullName: String
    private lateinit var email: String
    private lateinit var password: String
    private lateinit var confirmPassword: String
    private lateinit var phoneNumber: String
    private lateinit var doctor: String
    private fun createIntentWithGender(gender: String): Intent {
        val intent = Intent(this, age_pat::class.java)
        intent.putExtras(intent.extras ?: Bundle()) // Copier les extras existants
        intent.putExtra("gender", gender)
        return intent
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gender_pat)
        fullName = intent.getStringExtra("fullName") ?: ""
        email = intent.getStringExtra("email") ?: ""
        password = intent.getStringExtra("password") ?: ""
        confirmPassword = intent.getStringExtra("passwordconf") ?: ""
        phoneNumber = intent.getStringExtra("phoneNumber") ?: ""
        doctor = intent.getStringExtra("doctor") ?: ""
        val imageViewMale: ImageView = findViewById(R.id.Male)
        val imageViewFemale: ImageView = findViewById(R.id.Female)

        imageViewMale.setOnClickListener {
            val intent = createIntentWithGender("Male").apply {

                putExtra("fullName", fullName)
                putExtra("email", email)
                putExtra("password", password)
                putExtra("passwordconf", confirmPassword)
                putExtra("phoneNumber", phoneNumber)
                putExtra("doctor", doctor)
            }

            startNextActivity(intent)
        }

        // Ajoutez un écouteur de clic à l'image féminine
        imageViewFemale.setOnClickListener {
            val intent = createIntentWithGender("Female").apply {

                putExtra("fullName", fullName)
                putExtra("email", email)
                putExtra("password", password)
                putExtra("passwordconf", confirmPassword)
                putExtra("phoneNumber", phoneNumber)
                putExtra("doctor", doctor)
            }

            startNextActivity(intent)
        }
    }

    private fun startNextActivity(intent: Intent) {
        startActivity(intent)
    }

}