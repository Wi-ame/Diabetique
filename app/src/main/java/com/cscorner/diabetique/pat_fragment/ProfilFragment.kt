package com.cscorner.diabetique.pat_fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.cscorner.diabetique.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class ProfilFragment : Fragment() {
    private lateinit var fullNameTextView: TextView
    private lateinit var emailTextView: TextView
    private lateinit var age: TextView
    private lateinit var phoneNumberTextView: TextView
    private lateinit var Poids: TextView
    private lateinit var Taille: TextView
    private lateinit var activite: TextView
    private lateinit var allergie: TextView
    private lateinit var diabete : TextView

    private val databaseReference = FirebaseDatabase.getInstance().reference
    private val currentUser = FirebaseAuth.getInstance().currentUser
    private lateinit var settingsTextView: TextView




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_profil, container, false)

        // Initialize views
        fullNameTextView = view.findViewById(R.id.textViewName)
        emailTextView = view.findViewById(R.id.textViewEmail)
        phoneNumberTextView = view.findViewById(R.id.textViewPhoneNumber)
        age=view.findViewById(R.id.textViewAge)
        allergie=view.findViewById(R.id.textViewAllergie)
        activite=view.findViewById(R.id.TextViewActivite)
        Poids =view. findViewById(R.id.textViewPoids)
        Taille=view.findViewById(R.id.textViewTaille)
        settingsTextView = view.findViewById(R.id.textViewSettings)
        diabete=view.findViewById(R.id.textViewdiabete)
        currentUser?.email?.let { email ->
            databaseReference.child("patients").orderByChild("email").equalTo(email)
                .addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        for (data in snapshot.children) {
                            val fullName = data.child("fullName").getValue(String::class.java)
                            val email = data.child("email").getValue(String::class.java)
                            val phoneNumber = data.child("phoneNumber").getValue(String::class.java)
                            val Age = data.child("age").getValue(String::class.java)
                            val Allergie= data.child("allergie").getValue(String::class.java)
                            val Activité = data.child("activite").getValue(String::class.java)
                            val poids = data.child("poids").getValue(String::class.java)
                            val taille = data.child("taille").getValue(String::class.java)
                            val di = data.child("diabete").getValue(String::class.java)
                            val doctor = data.child("doctor").getValue(String::class.java)

                            fullNameTextView.text = fullName
                            emailTextView.text = email
                            phoneNumberTextView.text = phoneNumber
                            age.text=Age
                            allergie.text=Allergie
                            activite.text=Activité
                            Poids.text=poids
                            Taille.text=taille
                            diabete.text=di


                        }
                    }

                    override fun onCancelled(error: DatabaseError) {

                    }
                })
        }
        return view
    }


    }
