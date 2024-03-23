package com.cscorner.diabetique

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.lifecycle.ViewModelProvider

class Information : AppCompatActivity() {
    private var viewModel: Patient = Patient()
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
        viewModel = ViewModelProvider(this).get(Patient::class.java)

        buttonNext.setOnClickListener {
            val fullName = editTextFullName.text.toString()
            val email = editTextEmail.text.toString()
            val password = editTextPassword.text.toString()
            val confirmPassword = editTextConfirmPassword.text.toString()
            val phoneNumber = editTextPhoneNumber.text.toString()
            val doctor = editTextDoctor.text.toString()

            viewModel.savePage1Data(fullName, email, password, confirmPassword, phoneNumber, doctor)
            val intent = Intent(this, gender_pat::class.java)
            intent.putExtra("fullName", fullName)
            intent.putExtra("email", email)
            intent.putExtra("password", password)
            intent.putExtra("passwordconf", confirmPassword)
            intent.putExtra("phoneNumber", phoneNumber)
            intent.putExtra("doctor", doctor)



            startActivity(intent)

        }
    }

}