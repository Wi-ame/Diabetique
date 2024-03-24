package com.cscorner.diabetique

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import androidx.lifecycle.ViewModelProvider

class age_pat : AppCompatActivity() {
    private lateinit var fullName: String
    private lateinit var email: String
    private lateinit var password: String
    private lateinit var confirmPassword: String
    private lateinit var phoneNumber: String
    private lateinit var doctor: String
    private lateinit var gender: String
    private fun createIntentWithAge(age: String): Intent {
        val intent = Intent(this,Diabete::class.java)
        intent.putExtras(intent.extras ?: Bundle())
        intent.putExtra("age", age)
        return intent
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_age_pat)
        fullName = intent.getStringExtra("fullName") ?: ""
        email = intent.getStringExtra("email") ?: ""
        password = intent.getStringExtra("password") ?: ""
        confirmPassword = intent.getStringExtra("passwordconf") ?: ""
        phoneNumber = intent.getStringExtra("phoneNumber") ?: ""
        doctor = intent.getStringExtra("doctor") ?: ""
        gender =intent.getStringExtra("gender") ?: ""

        val imageViewYoung: ImageView = findViewById(R.id.Age_18_33)
        val imageViewMature: ImageView = findViewById(R.id.Age_33_44)
        val imageViewAvant: ImageView = findViewById(R.id.Age_44_55)
        val imageViewLast: ImageView = findViewById(R.id.Age_55_plus)

        imageViewYoung.setOnClickListener {

            val intent = createIntentWithAge("18-33").apply{
                putExtra("fullName", fullName)
                putExtra("email", email)
                putExtra("password", password)
                putExtra("passwordconf", confirmPassword)
                putExtra("phoneNumber", phoneNumber)
                putExtra("doctor", doctor)
                putExtra("gender", gender)
            }
            startNextActivity(intent)
        }

        imageViewMature.setOnClickListener {
            val intent = createIntentWithAge("33-44").apply{
                putExtra("fullName", fullName)
                putExtra("email", email)
                putExtra("password", password)
                putExtra("passwordconf", confirmPassword)
                putExtra("phoneNumber", phoneNumber)
                putExtra("doctor", doctor)
                putExtra("gender", gender)
            }
            startNextActivity(intent)



        }

        imageViewAvant.setOnClickListener {
            val intent = createIntentWithAge("44-55").apply{
                putExtra("fullName", fullName)
                putExtra("email", email)
                putExtra("password", password)
                putExtra("passwordconf", confirmPassword)
                putExtra("phoneNumber", phoneNumber)
                putExtra("doctor", doctor)
                putExtra("gender", gender)
            }
            startNextActivity(intent)


        }

        imageViewLast.setOnClickListener {
            val intent = createIntentWithAge("55+").apply{
                putExtra("fullName", fullName)
                putExtra("email", email)
                putExtra("password", password)
                putExtra("passwordconf", confirmPassword)
                putExtra("phoneNumber", phoneNumber)
                putExtra("doctor", doctor)
                putExtra("gender", gender)
            }

            startNextActivity(intent)

        }
    }

    private fun startNextActivity(intent: Intent) {
        startActivity(intent)
    }

}