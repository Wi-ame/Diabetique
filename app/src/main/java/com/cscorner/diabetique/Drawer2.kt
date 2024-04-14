package com.cscorner.diabetique

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.cscorner.diabetique.pat_fragment.DoctorFragment
import com.cscorner.diabetique.pat_fragment.HomeFragment
import com.cscorner.diabetique.pat_fragment.ProfilFragment
import com.cscorner.diabetique.pat_fragment.SettingsFragment
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import doct_fragment.MenuFragment

class Drawer2 : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var drawerLayout2: DrawerLayout
    private lateinit var navigationView: NavigationView
    private lateinit var toolbar: androidx.appcompat.widget.Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_drawer2)

        drawerLayout2 = findViewById(R.id.drawer_layout2) // Correction ici
        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        navigationView = findViewById(R.id.nav_view2)
        navigationView.setNavigationItemSelectedListener(this)

        val toggle = ActionBarDrawerToggle(
            this, drawerLayout2, toolbar,
            R.string.open_nav,
            R.string.close_nav
        )
        drawerLayout2.addDrawerListener(toggle)
        toggle.syncState()

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, HomeFragment())
                .commit()
            navigationView.setCheckedItem(R.id.nav_home)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_home -> replaceFragment(HomeFragment())
            R.id.nav_settings -> replaceFragment(SettingsFragment())
            R.id.nav_menu -> replaceFragment(MenuFragment())
            R.id.nav_profile -> replaceFragment(ProfilFragment())
            R.id.nav_med -> replaceFragment(DoctorFragment())
            R.id.nav_logout -> {
                // Récupérer l'utilisateur actuellement connecté
                val currentUser = FirebaseAuth.getInstance().currentUser

                if (currentUser != null) {
                    // Référence à la base de données
                    val usersRef = FirebaseDatabase.getInstance().reference.child("patients")

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
                val intent = Intent(this@Drawer2, Pat_Auth::class.java)
                startActivity(intent)
                finish()
            }


        }
        drawerLayout2.closeDrawer(GravityCompat.START)
        return true
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }
}
