package com.cscorner.diabetique
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
class Information : AppCompatActivity() {
    private lateinit var editTextFullName: EditText
    private lateinit var editTextEmail: EditText
    private lateinit var editTextPassword: EditText
    private lateinit var editTextConfirmPassword: EditText
    private lateinit var editTextPhoneNumber: EditText
    private lateinit var editTextDoctor: EditText
    private lateinit var buttonNext: Button
    private lateinit var textViewErrorPhoneNumber: TextView
    private lateinit var database: FirebaseDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_information)
        database = FirebaseDatabase.getInstance()

        editTextFullName = findViewById(R.id.editTextFullName)
        editTextEmail = findViewById(R.id.editTextEmail)
        editTextPassword = findViewById(R.id.editTextPassword)
        editTextConfirmPassword = findViewById(R.id.editTextConfirmPassword)
        editTextPhoneNumber = findViewById(R.id.editTextPhoneNumber)
        editTextDoctor = findViewById(R.id.editTextDoctor)
        buttonNext = findViewById(R.id.buttonNext)
        buttonNext.setOnClickListener {
            val fullName = editTextFullName.text.toString()
            val email = editTextEmail.text.toString()
            val password = editTextPassword.text.toString()
            val confirmPassword = editTextConfirmPassword.text.toString()
            val phoneNumber = editTextPhoneNumber.text.toString()
            val doctor = editTextDoctor.text.toString()

            if (fullName.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty() &&
                confirmPassword.isNotEmpty() && phoneNumber.isNotEmpty() && doctor.isNotEmpty()
            ) {
                if (isValidEmail(email) && isValidPhoneNumber(phoneNumber)) {
                    if (password != confirmPassword) {
                        Toast.makeText(this@Information, "Les mots de passe ne correspondent pas", Toast.LENGTH_SHORT).show()
                        return@setOnClickListener
                    }

                    checkExistingDoctor(doctor) { doctorExists ->
                        if (doctorExists) {
                            // Le médecin n'existe pas encore, vous pouvez maintenant vérifier le patient
                            val intent = Intent(this, gender_pat::class.java)
                            intent.putExtra("fullName", fullName)
                            intent.putExtra("email", email)
                            intent.putExtra("password", password)
                            intent.putExtra("passwordconf", confirmPassword)
                            intent.putExtra("phoneNumber", phoneNumber)
                            intent.putExtra("doctor", doctor)

                            checkExistingPatient(email, phoneNumber) { patientExists ->
                                if (patientExists) {
                                    Toast.makeText(
                                        this@Information,
                                        "Cet email ou ce numéro de téléphone est déjà utilisé par un autre utilisateur",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                } else {
                                    // L'email et le numéro de téléphone sont uniques, passez à l'étape suivante
                                    startActivity(intent)
                                }
                            }
                        } else {
                            Toast.makeText(
                                this@Information,
                                "Ce médecin n'existe ps",
                                Toast.LENGTH_SHORT
                            ).show()

                        }
                    }
                } else {
                    Toast.makeText(this, "Veuillez entrer une adresse email valide et un numéro de téléphone valide", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Tous les champs sont obligatoires", Toast.LENGTH_SHORT).show()
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

    private fun checkExistingDoctor(doctor: String, callback: (Boolean) -> Unit) {
        val reference = database.getReference("doctors")

        reference.orderByChild("email").equalTo(doctor)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    callback(dataSnapshot.exists())
                }
                override fun onCancelled(databaseError: DatabaseError) {
                    callback(true)
                }
            })
    }

    private fun checkExistingPatient(email: String, phoneNumber: String, callback: (Boolean) -> Unit) {
        val reference = database.getReference("patients")

        reference.orderByChild("email").equalTo(email)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    if (dataSnapshot.exists()) {
                        callback(true)
                    } else {
                        reference.orderByChild("phoneNumber").equalTo(phoneNumber)
                            .addListenerForSingleValueEvent(object : ValueEventListener {
                                override fun onDataChange(dataSnapshot: DataSnapshot) {
                                    if (dataSnapshot.exists()) {
                                        callback(true)
                                    } else {
                                        callback(false)
                                    }
                                }

                                override fun onCancelled(databaseError: DatabaseError) {
                                    callback(true)
                                }
                            })
                    }
                }
                override fun onCancelled(databaseError: DatabaseError) {
                    callback(true)
                }
            })
    }
}


