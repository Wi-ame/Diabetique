package com.cscorner.diabetique
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class Doctor_Auth : AppCompatActivity() {
    private lateinit var usernameEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var loginButton: Button
    private lateinit var signupRedirectText: TextView
    private lateinit var auth: FirebaseAuth
    private lateinit var databaseReference: DatabaseReference
    private lateinit var Forgotpass:TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_doctor_auth)
        auth = FirebaseAuth.getInstance()
        databaseReference = FirebaseDatabase.getInstance().reference.child("doctors")

        usernameEditText = findViewById(R.id.login_username)
        passwordEditText = findViewById(R.id.login_password)
        loginButton = findViewById(R.id.login_button)
        signupRedirectText = findViewById(R.id.signupRedirectText)
        Forgotpass = findViewById(R.id.forgotText)
        val currentUser = auth.currentUser
        val email = usernameEditText.text.toString()

                if (currentUser != null) {
                    startDrawerActivity(email)
                }
                    loginButton.setOnClickListener {

                        val email = usernameEditText.text.toString()
                        val password = passwordEditText.text.toString()
                        if (email.isNotEmpty() && password.isNotEmpty()) {
                            signIn(email, password)
                        } else {
                            Toast.makeText(
                                this@Doctor_Auth,
                                "Veuillez entrer votre email et votre mot de passe",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }


        signupRedirectText.setOnClickListener {
            startDoctorRegistrationActivity()
        }
        Forgotpass.setOnClickListener {
            val email = usernameEditText.text.toString()
            if (email.isNotEmpty()) {
                // Vérifier si l'email existe dans la base de données des docteurs
                databaseReference.orderByChild("email").equalTo(email).addListenerForSingleValueEvent(object :
                    ValueEventListener {
                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        if (dataSnapshot.exists()) {
                            // L'email existe dans la base de données des docteurs, envoyer le lien de réinitialisation par email
                            auth.sendPasswordResetEmail(email)
                                .addOnCompleteListener { task ->
                                    if (task.isSuccessful) {
                                        // L'email de réinitialisation a été envoyé avec succès
                                        Toast.makeText(
                                            this@Doctor_Auth,
                                            "Un email de réinitialisation a été envoyé à $email",
                                            Toast.LENGTH_SHORT
                                        ).show()

                                        // Demander à l'utilisateur de se reconnecter avec le nouveau mot de passe
                                        Toast.makeText(
                                            this@Doctor_Auth,
                                            "Veuillez vous reconnecter avec votre nouveau mot de passe.",
                                            Toast.LENGTH_SHORT
                                        ).show()


                                    } else {
                                        // Échec de l'envoi de l'email de réinitialisation
                                        Toast.makeText(
                                            this@Doctor_Auth,
                                            "Erreur : ${task.exception?.message}",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }
                                }
                        } else {
                            // L'email n'existe pas dans la base de données des docteurs
                            Toast.makeText(
                                this@Doctor_Auth,
                                "Aucun compte associé à cet email",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }

                    override fun onCancelled(databaseError: DatabaseError) {
                        // Gérer l'annulation
                        Toast.makeText(this@Doctor_Auth, "Erreur : ${databaseError.message}", Toast.LENGTH_SHORT).show()
                    }
                })
            } else {
                // Champ email vide
                Toast.makeText(this@Doctor_Auth, "Veuillez entrer votre email", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun startDrawerActivity(email: String) {
        val intent = Intent(this, Drawer::class.java)
        intent.putExtra("email", email)
        startActivity(intent)
        finish()
    }
    private fun startDoctorRegistrationActivity() {
        val intent = Intent(this, Inscr_Doc::class.java)
        startActivity(intent)
    }
    private fun signIn(email: String, password: String) {
        // Authentification de l'utilisateur avec le nouvel email et le nouveau mot de passe
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Connexion réussie, récupérer l'utilisateur actuel
                    val currentUser = auth.currentUser
                    currentUser?.let {
                        // Mettre à jour le mot de passe dans la base de données Firebase Realtime Database
                        databaseReference.child(currentUser.uid).child("password").setValue(password)
                            .addOnSuccessListener {
                                // Succès de la mise à jour du mot de passe dans la base de données
                                Toast.makeText(
                                    this@Doctor_Auth,
                                    "Connexion réussie !",
                                    Toast.LENGTH_SHORT
                                ).show()

                                // Démarrer l'activité Drawer avec l'e-mail de l'utilisateur
                                startDrawerActivity(email)
                                // Effacer les champs d'entrée
                                usernameEditText.text.clear()
                                passwordEditText.text.clear()
                            }
                            .addOnFailureListener { e ->
                                // Échec de la mise à jour du mot de passe dans la base de données
                                Toast.makeText(
                                    this@Doctor_Auth,
                                    "Erreur lors de la mise à jour du mot de passe dans la base de données: ${e.message}",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                    }
                } else {
                    // Échec de la connexion
                    Toast.makeText(
                        this@Doctor_Auth,
                        "Mot de passe incorrect: ${task.exception?.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }


}