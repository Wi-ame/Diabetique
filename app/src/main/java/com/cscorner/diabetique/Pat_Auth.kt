package com.cscorner.diabetique

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
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

class Pat_Auth : AppCompatActivity() {
    private lateinit var usernameEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var loginButton: Button
    private lateinit var signupRedirectText: TextView
    private lateinit var Forgotpass: TextView
    private lateinit var auth: FirebaseAuth
    private lateinit var databaseReference: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pat_auth)

        // Initialiser Firebase Auth
        auth = FirebaseAuth.getInstance()

        // Vérifier si l'utilisateur est déjà connecté
        val currentUser = auth.currentUser
        if (currentUser != null) {
            // L'utilisateur est déjà connecté, rediriger vers l'activité suivante
            startnextActivity()
        }

        // Initialiser les vues et les écouteurs d'événements ici
        usernameEditText = findViewById(R.id.login_username)
        passwordEditText = findViewById(R.id.login_password)
        loginButton = findViewById(R.id.login_button)
        signupRedirectText = findViewById(R.id.signupRedirectText)
        Forgotpass = findViewById(R.id.forgotText)
        databaseReference = FirebaseDatabase.getInstance().reference

        // Initialiser l'écouteur de clic pour le bouton de connexion
        loginButton.setOnClickListener {
            val email = usernameEditText.text.toString()
            val password = passwordEditText.text.toString()
            if (email.isNotEmpty() && password.isNotEmpty()) {
                signIn(email, password)
            } else {
                Toast.makeText(
                    this@Pat_Auth,
                    "Veuillez entrer votre email et votre mot de passe",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        // Initialiser l'écouteur de clic pour le texte de redirection vers l'inscription
        signupRedirectText.setOnClickListener {

            startDrawerActivity()
        }

        // Initialiser l'écouteur de clic pour le texte de récupération du mot de passe
        Forgotpass.setOnClickListener {
            val email = usernameEditText.text.toString()
            if (email.isNotEmpty()) {
                // Vérifier si l'email existe dans la base de données des docteurs
                databaseReference.orderByChild("email").equalTo(email)
                    .addListenerForSingleValueEvent(object :
                        ValueEventListener {
                        override fun onDataChange(dataSnapshot: DataSnapshot) {
                            if (dataSnapshot.exists()) {
                                // L'email existe dans la base de données des docteurs, envoyer le lien de réinitialisation par email
                                auth.sendPasswordResetEmail(email)
                                    .addOnCompleteListener { task ->
                                        if (task.isSuccessful) {
                                            // L'email de réinitialisation a été envoyé avec succès
                                            Toast.makeText(
                                                this@Pat_Auth,
                                                "Un email de réinitialisation a été envoyé à $email",
                                                Toast.LENGTH_SHORT
                                            ).show()

                                            // Demander à l'utilisateur de se reconnecter avec le nouveau mot de passe
                                            Toast.makeText(
                                                this@Pat_Auth,
                                                "Veuillez vous reconnecter avec votre nouveau mot de passe.",
                                                Toast.LENGTH_SHORT
                                            ).show()
                                        } else {
                                            // Échec de l'envoi de l'email de réinitialisation
                                            Toast.makeText(
                                                this@Pat_Auth,
                                                "Erreur : ${task.exception?.message}",
                                                Toast.LENGTH_SHORT
                                            ).show()
                                        }
                                    }
                            } else {
                                // L'email n'existe pas dans la base de données des docteurs
                                Toast.makeText(
                                    this@Pat_Auth,
                                    "Aucun compte associé à cet email",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }

                        override fun onCancelled(databaseError: DatabaseError) {
                            // Gérer l'annulation
                            Toast.makeText(
                                this@Pat_Auth,
                                "Erreur : ${databaseError.message}",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    })
            } else {
                // Champ email vide
                Toast.makeText(this@Pat_Auth, "Veuillez entrer votre email", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    private fun startDrawerActivity() {
        val intent = Intent(this, Information::class.java)
        startActivity(intent)
        finish() // Facultatif : fermer l'activité actuelle si nécessaire
    }

    private fun startnextActivity() {
        val intent = Intent(this, Drawer2::class.java)
        startActivity(intent)
        finish() // Facultatif : fermer l'activité actuelle si nécessaire
    }

    fun updateUserStatus(userId: String, status: String) {
        val patientsRef = FirebaseDatabase.getInstance().reference.child("patients").child(userId)
        patientsRef.child("statut").setValue(status)
            .addOnSuccessListener {
                // Succès de la mise à jour du statut
                Log.d("Firebase", "Statut utilisateur mis à jour avec succès : $status")
            }
            .addOnFailureListener { e ->
                // Échec de la mise à jour du statut
                Log.e(
                    "Firebase",
                    "Erreur lors de la mise à jour du statut utilisateur : ${e.message}"
                )
            }
    }

    private fun signIn(email: String, password: String) {
        val usersRef = FirebaseDatabase.getInstance().reference.child("patients")
        val query = usersRef.orderByChild("email").equalTo(email)
        query.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    // L'utilisateur avec cet email existe dans la base de données
                    for (userSnapshot in snapshot.children) {
                        val userId = userSnapshot.key // Récupérer l'ID de l'utilisateur
                        if (!userId.isNullOrEmpty()) {
                            // Authentifier l'utilisateur avec l'email et le mot de passe fournis
                            auth.signInWithEmailAndPassword(email, password)
                                .addOnCompleteListener { task ->
                                    if (task.isSuccessful) {
                                        // Connexion réussie, récupérer l'utilisateur actuel
                                        val currentUser = auth.currentUser
                                        currentUser?.let {
                                            // Mettre à jour le statut en ligne dès la connexion
                                            updateUserStatus(userId, "En ligne")
                                            // Rediriger l'utilisateur vers l'activité appropriée
                                            startnextActivity()
                                            // Effacer les champs d'entrée
                                            usernameEditText.text.clear()
                                            passwordEditText.text.clear()
                                        }
                                    } else {
                                        // Échec de la connexion
                                        Toast.makeText(
                                            this@Pat_Auth,
                                            "Mot de passe incorrect: ${task.exception?.message}",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }
                                }
                        } else {
                            // ID de l'utilisateur est null ou vide
                            Log.e("Firebase", "ID de l'utilisateur est null ou vide")
                        }
                    }
                } else {
                    // Aucun utilisateur trouvé avec cet email dans la base de données
                    Toast.makeText(
                        this@Pat_Auth,
                        "Aucun utilisateur trouvé avec cet email",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onCancelled(error: DatabaseError) {
                // Gérer l'erreur lors de la récupération de l'utilisateur
                Log.e("Firebase", "Erreur lors de la récupération de l'utilisateur: $error")
            }
        })
    }

}