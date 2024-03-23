package com.cscorner.diabetique

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.database.FirebaseDatabase

class Allergie : AppCompatActivity() {
    private lateinit var editTextAllergie: EditText
    private lateinit var buttonNext: Button
    private var viewModel: Patient = Patient()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_allergie)
        val fullName = intent.getStringExtra("fullName")
        val email = intent.getStringExtra("email")
        val password = intent.getStringExtra("password")
        val confirmPassword = intent.getStringExtra("confirmPassword")
        val phoneNumber = intent.getStringExtra("phoneNumber")
        val doctor = intent.getStringExtra("doctor")
        viewModel = ViewModelProvider(this).get(Patient::class.java)
        editTextAllergie = findViewById(R.id.editTextAllergie)
        buttonNext = findViewById(R.id.buttonNext)

        // Ajouter un OnClickListener au bouton "Next"
        buttonNext.setOnClickListener {
            val builder = AlertDialog.Builder(this)

            val allergieText = editTextAllergie.text.toString().trim()
            // Vérifier si le champ d'allergie est vide ou non
            if (allergieText.isNotEmpty()) {
                // Utilisez l'instance viewModel pour appeler les fonctions savePage8Data() et saveDataToFirebase()
                viewModel.savePage8Data(allergieText)
                saveDataFirebase(fullName, email, password, confirmPassword, phoneNumber, doctor)
            } else {
                // Utilisez l'instance viewModel pour appeler les fonctions savePage8Data() et saveDataToFirebase()
                viewModel.savePage8Data("Non")
                viewModel.saveDataToFirebase()
            }
            builder.setTitle("Confirmation")
            builder.setMessage("Êtes-vous sûr de vouloir continuer ?")

            builder.setPositiveButton("Oui") { dialog, _ ->
                // Supprimez l'appel saveDataToFirebase() d'ici, car il est déjà appelé précédemment
                // Afficher un message de succès
                Toast.makeText(this, "Données enregistrées avec succès", Toast.LENGTH_SHORT).show()
                dialog.dismiss()
            }
            builder.setNegativeButton("Non") { dialog, _ ->
                val intent = Intent(this, Pat_Auth::class.java)
                startActivity(intent)
                dialog.dismiss()
            }
            val dialog = builder.create()
            dialog.show()
        }
    }
    fun saveDataFirebase(
        fullName: String?,
        email: String?,
        password: String?,
        confirmPassword: String?,
        phoneNumber: String?,
        doctor: String?
    ) {

        // Maintenant, vous pouvez enregistrer ces données dans la base de données Firebase
        val database = FirebaseDatabase.getInstance().reference.child("patients")
        val patientId = database.push().key // Génère une clé unique pour le patient
        val patientData = hashMapOf(
            "fullName" to fullName,
            "email" to email,
            "password" to password,
            "confirmPassword" to confirmPassword,
            "phoneNumber" to phoneNumber,
            "doctor" to doctor
            // Ajoutez d'autres champs si nécessaire
        )

        patientId?.let { id ->
            database.child(id).setValue(patientData)
                .addOnSuccessListener {
                    // Gestion de la réussite de l'enregistrement
                    println("Données enregistrées avec succès pour le patient avec l'ID: $id")
                }
                .addOnFailureListener { exception ->
                    // Gestion de l'échec de l'enregistrement
                    println("Échec de l'enregistrement des données pour le patient avec l'ID: $id. Erreur: ${exception.message}")
                }
        }
    }



    }
