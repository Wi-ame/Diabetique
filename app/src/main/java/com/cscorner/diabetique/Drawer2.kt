package com.cscorner.diabetique

import android.app.Notification
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.cscorner.diabetique.pat_fragment.HomeFragment
import com.cscorner.diabetique.pat_fragment.NotificationFragment
import com.cscorner.diabetique.pat_fragment.ProfilFragment
import com.cscorner.diabetique.pat_fragment.SettingsFragment
import com.google.android.material.navigation.NavigationView
import doct_fragment.ProfileFragment

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
            R.id.nav_notification -> replaceFragment(NotificationFragment())
            R.id.nav_profile -> replaceFragment(ProfilFragment())
            R.id.nav_logout -> {
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
