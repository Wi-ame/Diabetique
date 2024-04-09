package com.cscorner.diabetique.pat_fragment

import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.cscorner.diabetique.ChatActivity
import com.cscorner.diabetique.Patient
import com.cscorner.diabetique.R
import com.cscorner.diabetique.models.Conversation
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class DoctorFragment : Fragment() {
    private lateinit var textViewName: TextView
    private lateinit var textViewEmail: TextView
    private lateinit var textViewPhoneNumber: TextView
   private lateinit var chat :Button
    private lateinit var textViewAddress: TextView
    private val databaseReference = FirebaseDatabase.getInstance().reference
    private val currentUser = FirebaseAuth.getInstance().currentUser



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_doctor, container, false)

        textViewName = view.findViewById(R.id.textViewName)
        textViewEmail = view.findViewById(R.id.textViewEmail)
        textViewPhoneNumber = view.findViewById(R.id.textViewPhoneNumber)
        textViewAddress = view.findViewById(R.id.textViewAddress)
        chat=view.findViewById(R.id.message_button)
        currentUser?.email?.let { patientEmail ->
            databaseReference.child("patients").orderByChild("email").equalTo(patientEmail)
                .addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        for (data in snapshot.children) {
                            val doctorEmail = data.child("doctor").getValue(String::class.java)
                            // Si l'email du docteur est récupéré avec succès
                            doctorEmail?.let { doctorEmail ->
                                // Maintenant, récupérer les détails du docteur en utilisant son email
                                databaseReference.child("doctors").orderByChild("email").equalTo(doctorEmail)
                                    .addListenerForSingleValueEvent(object : ValueEventListener {
                                        override fun onDataChange(doctorSnapshot: DataSnapshot) {
                                            for (doctorData in doctorSnapshot.children) {
                                                val doctorName = doctorData.child("fullName").getValue(String::class.java)
                                                val doctorEmail = doctorData.child("email").getValue(String::class.java)
                                                val doctorPhoneNumber = doctorData.child("phoneNumber").getValue(String::class.java)
                                                val doctorAddress = doctorData.child("addresse").getValue(String::class.java)
                                                // Mettre à jour les TextView avec les détails du docteur
                                                textViewName.text = doctorName
                                                textViewEmail.text = doctorEmail
                                                textViewPhoneNumber.text = doctorPhoneNumber
                                                textViewAddress.text = doctorAddress
                                            }
                                        }

                                        override fun onCancelled(error: DatabaseError) {
                                            // Gérer l'erreur lors de la récupération des détails du docteur
                                        }
                                    })
                            } ?: run {
                                // Si l'email du docteur est null
                                Log.e("Doctor Fragment", "Email du docteur non trouvé pour le patient: $patientEmail")
                            }
                        }
                    }

                    override fun onCancelled(error: DatabaseError) {
                        // Gérer l'erreur lors de la récupération du champ doctor du patient
                    }
                })
        }
        chat.setOnClickListener {

        }


        return view

    }





}
