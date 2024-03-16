package com.cscorner.diabetique

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView

class Pat_Auth : AppCompatActivity() {
    private lateinit var usernameEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var loginButton: Button
    private lateinit var signupRedirectText: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pat_auth)

        usernameEditText = findViewById(R.id.login_username)
        passwordEditText = findViewById(R.id.login_password)
        loginButton = findViewById(R.id.login_button)
        signupRedirectText = findViewById(R.id.signupRedirectText)

        // Ajoutez un gestionnaire d'événements au bouton de connexion
        signupRedirectText.setOnClickListener {
            // Ici, vous pouvez ajouter la logique d'authentification si nécessaire
            // Pour l'exemple, simplement démarrer l'activité Drawer
            startDrawerActivity()
        }
    }
    private fun startDrawerActivity() {
        val intent = Intent(this, Information::class.java)
        startActivity(intent)
        finish() // Facultatif : fermer l'activité actuelle si nécessaire
    }

}