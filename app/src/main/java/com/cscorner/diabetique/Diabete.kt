package com.cscorner.diabetique

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import androidx.lifecycle.ViewModelProvider

class Diabete : AppCompatActivity() {
    private lateinit var fullName: String
    private lateinit var email: String
    private lateinit var password: String
    private lateinit var confirmPassword: String
    private lateinit var phoneNumber: String
    private lateinit var doctor: String
    private lateinit var gender: String
    private lateinit var age: String
    private fun createIntentWithDiabete(diabete: String): Intent {
        val intent = Intent(this, poid_pat::class.java)
        intent.putExtras(intent.extras ?: Bundle())
        intent.putExtra("diabete", diabete)
        return intent
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_diabete)
        val imageViewType1: ImageView = findViewById(R.id.Type1)
        val imageViewType2: ImageView = findViewById(R.id.Type2)
        fullName = intent.getStringExtra("fullName") ?: ""
        email = intent.getStringExtra("email") ?: ""
        password = intent.getStringExtra("password") ?: ""
        confirmPassword = intent.getStringExtra("passwordconf") ?: ""
        phoneNumber = intent.getStringExtra("phoneNumber") ?: ""
        doctor = intent.getStringExtra("doctor") ?: ""
        gender =intent.getStringExtra("gender") ?: ""
        age = intent.getStringExtra("age")?: ""
        imageViewType1.setOnClickListener {
            val intent = createIntentWithDiabete("Type1").apply {
                putExtra("fullName", fullName)
                putExtra("email", email)
                putExtra("password", password)
                putExtra("passwordconf", confirmPassword)
                putExtra("phoneNumber", phoneNumber)
                putExtra("doctor", doctor)
                putExtra("gender", gender)
                putExtra("age", age)
            }
            startNextActivity(intent)
        }

        imageViewType2.setOnClickListener {
            val intent = createIntentWithDiabete("Type2").apply {
                putExtra("fullName", fullName)
                putExtra("email", email)
                putExtra("password", password)
                putExtra("passwordconf", confirmPassword)
                putExtra("phoneNumber", phoneNumber)
                putExtra("doctor", doctor)
                putExtra("gender", gender)
                putExtra("age", age)
            }
            startNextActivity(intent)
        }

    }
    private fun startNextActivity(intent: Intent) {
        startActivity(intent)
    }
}
