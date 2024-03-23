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
    private lateinit var fullName: String
    private lateinit var email: String
    private lateinit var password: String
    private lateinit var confirmPassword: String
    private lateinit var phoneNumber: String
    private lateinit var doctor: String
    private lateinit var gender: String
    private lateinit var  age :String
    private lateinit var diabete :String
    private lateinit var poids :String
    private lateinit var taille:String
    private lateinit var activite :String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_allergie)
        // Récupérer les données transmises par l'intent
        fullName = intent.getStringExtra("fullName") ?: ""
        email = intent.getStringExtra("email") ?: ""
        password = intent.getStringExtra("password") ?: ""
        confirmPassword = intent.getStringExtra("passwordconf") ?: ""
        phoneNumber = intent.getStringExtra("phoneNumber") ?: ""
        doctor = intent.getStringExtra("doctor") ?: ""
        gender = intent.getStringExtra("gender") ?: ""
        age = intent.getStringExtra("age") ?: ""
        diabete = intent.getStringExtra("diabete") ?: ""
         poids = intent.getStringExtra("poids") ?: ""
         taille = intent.getStringExtra("taille") ?: ""
        activite =intent.getStringExtra("activite")?: ""
        editTextAllergie = findViewById(R.id.editTextAllergie)
        buttonNext = findViewById(R.id.buttonNext)
        // Ajouter un OnClickListener au bouton "Next"
        buttonNext.setOnClickListener {
            val builder = AlertDialog.Builder(this)

            val full = fullName.toString().trim()
            val email = email.toString().trim()
            val pass = password.toString().trim()
            val conf =confirmPassword.toString().trim()
            val phone =phoneNumber.toString().trim()
            val doctor  = doctor.toString().trim()
            val allergieText = editTextAllergie.text.toString().trim()
            val gender =gender.toString().trim()
            val age  = age.toString().trim()
            val diabete =diabete.toString().trim()
            val poids =poids.toString().trim()
            val taille =taille.toString().trim()
            val activite =activite.toString().trim()



            // Vérifier si le champ d'allergie est vide ou non
            if (allergieText.isNotEmpty()) {
                // Afficher une boîte de dialogue pour confirmer
                builder.setTitle("Confirmation")
                builder.setMessage("Êtes-vous sûr de vouloir continuer ?")
                builder.setPositiveButton("Oui") { dialog, _ ->
                    val database = FirebaseDatabase.getInstance()
                    val reference = database.getReference("patients")
                    val patientId = reference.push().key ?: ""
                    val patientData = Patient(full,email,pass,conf,phone,doctor,allergieText, gender,age,diabete,poids,taille,activite)
                    reference.child(patientId!!).setValue(patientData)
                        .addOnSuccessListener {

                            Toast.makeText(this@Allergie, "Les informations ont été insérées avec succès", Toast.LENGTH_SHORT).show()

                            val intent = Intent(this@Allergie, Doctor_Auth::class.java)
                            startActivity(intent)
                        }
                        .addOnFailureListener {
                            Toast.makeText(this@Allergie ,"Erreur : Les informations n'ont pas été insérées", Toast.LENGTH_SHORT).show()
                        }

                    dialog.dismiss()
                }
                builder.setNegativeButton("Non") { dialog, _ ->
                    dialog.dismiss()
                }
                val dialog = builder.create()
                dialog.show()
            } else {
                builder.setTitle("Confirmation")
                builder.setMessage("Êtes-vous sûr de vouloir continuer ?")
                builder.setPositiveButton("Oui") { dialog, _ ->
                    val database = FirebaseDatabase.getInstance()
                    val reference = database.getReference("patients")
                    val patientId = reference.push().key ?: ""
                    val patientData = Patient(full,email,pass,conf,phone,doctor,"Non", gender,age,diabete,poids,taille,activite)
                    reference.child(patientId!!).setValue(patientData)
                        .addOnSuccessListener {

                            Toast.makeText(this@Allergie, "Les informations ont été insérées avec succès", Toast.LENGTH_SHORT).show()

                            val intent = Intent(this@Allergie, Doctor_Auth::class.java)
                            startActivity(intent)
                        }
                        .addOnFailureListener {
                            Toast.makeText(this@Allergie ,"Erreur : Les informations n'ont pas été insérées", Toast.LENGTH_SHORT).show()
                        }

                    dialog.dismiss()
                }
                builder.setNegativeButton("Non") { dialog, _ ->
                    dialog.dismiss()
                }
                val dialog = builder.create()
                dialog.show()

            }
        }
    }

}
