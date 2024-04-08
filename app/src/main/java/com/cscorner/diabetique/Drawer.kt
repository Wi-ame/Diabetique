package com.cscorner.diabetique

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import doct_fragment.HomeFragment
import doct_fragment.MenuFragment
import doct_fragment.ProfileFragment
import doct_fragment.SettingsFragment

class Drawer : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    private lateinit var appBarConfiguration: AppBarConfiguration

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navigationView: NavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_drawer)

        drawerLayout = findViewById(R.id.drawer_layout)
        val toolbar = findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController

        appBarConfiguration = AppBarConfiguration(
            setOf(R.id.homeFragment, R.id.settingsFragment),
            drawerLayout
        )

        setupActionBarWithNavController(navController, appBarConfiguration)
        navigationView = findViewById(R.id.nav_view)
        navigationView.setNavigationItemSelectedListener(this)

        val toggle = ActionBarDrawerToggle(
            this, drawerLayout, toolbar,
            R.string.open_nav,
            R.string.close_nav
        )
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        if (savedInstanceState == null) {
            // Si c'est la première fois que l'activité est créée, afficher le fragment HomeFragment
            val currentUserEmail = FirebaseAuth.getInstance().currentUser?.email ?: ""
            replaceFragment(HomeFragment(), currentUserEmail)
            navigationView.setCheckedItem(R.id.nav_home)
        }
    }


    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        when (item.itemId) {
            R.id.nav_home -> navController.navigate(R.id.homeFragment)
            R.id.nav_profile -> navController.navigate(R.id.profileFragment)
            R.id.nav_settings -> navController.navigate(R.id.settingsFragment)
            R.id.nav_menu -> navController.navigate(R.id.menuFragment)
            R.id.nav_logout -> {
                // Récupérer l'utilisateur actuellement connecté
                val currentUser = FirebaseAuth.getInstance().currentUser

                if (currentUser != null) {
                    // Référence à la base de données
                    val usersRef = FirebaseDatabase.getInstance().reference.child("doctors")

                    // Effectuer une requête pour récupérer l'utilisateur avec l'email correspondant
                    val query = usersRef.orderByChild("email").equalTo(currentUser.email)

                    // Ajouter un écouteur pour récupérer les données une fois que la requête est exécutée
                    query.addListenerForSingleValueEvent(object : ValueEventListener {
                        override fun onDataChange(snapshot: DataSnapshot) {
                            if (snapshot.exists()) {
                                // L'utilisateur avec cet email existe dans la base de données
                                for (userSnapshot in snapshot.children) {
                                    val userId = userSnapshot.key // Récupérer l'ID de l'utilisateur
                                    if (!userId.isNullOrEmpty()) {
                                        // Mettre à jour le statut de l'utilisateur à "Hors ligne"
                                        userSnapshot.child("statut").ref.setValue("Hors ligne")
                                            .addOnSuccessListener {
                                                Log.d("UserLogout", "Statut utilisateur mis à jour avec succès : Hors ligne")
                                            }
                                            .addOnFailureListener { e ->
                                                Log.e("UserLogout", "Erreur lors de la mise à jour du statut utilisateur : ${e.message}")
                                            }
                                    } else {
                                        Log.e("UserLogout", "Empty user ID")
                                    }
                                }
                            } else {
                                Log.e("UserLogout", "Aucun utilisateur trouvé avec cet email dans la base de données")
                            }
                        }

                        override fun onCancelled(error: DatabaseError) {
                            // Gérer l'erreur lors de la récupération de l'utilisateur
                            Log.e("Firebase", "Erreur lors de la récupération de l'utilisateur: $error")
                        }
                    })
                } else {
                    Log.e("UserLogout", "Current user is null")
                }

                // Se déconnecter de Firebase Auth
                FirebaseAuth.getInstance().signOut()

                // Rediriger vers l'activité de connexion
                val intent = Intent(this@Drawer, Doctor_Auth::class.java)
                startActivity(intent)
                finish()
            }

        }
        drawerLayout.closeDrawers()
        return true
    }


    private fun replaceFragment(fragment: Fragment, email: String = "") {
        val bundle = Bundle()
        bundle.putString("email", email)
        fragment.arguments = bundle

        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
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
                Log.e("Firebase", "Erreur lors de la mise à jour du statut utilisateur : ${e.message}")
            }
    }

}
