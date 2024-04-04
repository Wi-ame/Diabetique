package com.cscorner.diabetique

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class Inscr_Doc : AppCompatActivity() {
    private lateinit var editTextFullName: EditText
    private lateinit var editTextEmail: EditText
    private lateinit var editTextPassword: EditText
    private lateinit var editTextConfirmPassword: EditText
    private lateinit var editTextPhoneNumber: EditText
    private lateinit var editTextDoctor: EditText
    private lateinit var auth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inscr_doc)
        val buttonNext: Button = findViewById(R.id.buttonNext)
        editTextFullName = findViewById(R.id.editTextFullName)
        editTextEmail = findViewById(R.id.editTextEmail)
        editTextPassword = findViewById(R.id.editTextPassword)
        editTextConfirmPassword = findViewById(R.id.editTextConfirmPassword)
        editTextPhoneNumber = findViewById(R.id.editTextPhoneNumber)
        editTextDoctor = findViewById(R.id.editTextAddress)
        auth = FirebaseAuth.getInstance()

        // Ajouter un gestionnaire de clic au bouton "Next"
        buttonNext.setOnClickListener {
            val fullName = editTextFullName.text.toString()
            val email = editTextEmail.text.toString()
            val password = editTextPassword.text.toString()
            val confirmPassword = editTextConfirmPassword.text.toString()
            val phoneNumber = editTextPhoneNumber.text.toString()
            val doctorAddress = editTextDoctor.text.toString()

            if (fullName.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty() &&
                confirmPassword.isNotEmpty() && phoneNumber.isNotEmpty() && doctorAddress.isNotEmpty()
            ) {
                if (isValidEmail(email) && isValidPhoneNumber(phoneNumber)) {
                    if (password != confirmPassword) {
                        // Afficher un message d'erreur avec un Toast
                        Toast.makeText(this@Inscr_Doc, "Les mots de passe ne correspondent pas", Toast.LENGTH_SHORT).show()
                        return@setOnClickListener
                    }
                    auth.createUserWithEmailAndPassword(email, password)
                    val database = FirebaseDatabase.getInstance()
                    val reference = database.getReference("doctors")

                    val doctorId = reference.push().key

                    val doctor = Doctor(fullName, email, password, phoneNumber, doctorAddress)

                    reference.child(doctorId!!).setValue(doctor)
                        .addOnSuccessListener {
                            Toast.makeText(this@Inscr_Doc, "Les informations ont été insérées avec succès", Toast.LENGTH_SHORT).show()
                            val intent = Intent(this@Inscr_Doc, Doctor_Auth::class.java)
                            startActivity(intent)
                        }
                        .addOnFailureListener {
                            Toast.makeText(this@Inscr_Doc, "Erreur : Les informations n'ont pas été insérées", Toast.LENGTH_SHORT).show()
                        }
                } else {
                    Toast.makeText(this@Inscr_Doc, "Veuillez entrer une adresse email valide et un numéro de téléphone valide", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this@Inscr_Doc, "Tous les champs sont obligatoires", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun isValidPhoneNumber(phoneNumber: String): Boolean {
        val phonePattern = "^0[0-9]{9}$"
        return phoneNumber.matches(phonePattern.toRegex())
    }

    private fun isValidEmail(email: String): Boolean {
        val emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$"
        return email.matches(emailRegex.toRegex())
    }
}