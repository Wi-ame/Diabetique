package com.cscorner.diabetique.pat_fragment

import android.content.ContentValues
import android.content.ContentValues.TAG
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
import com.cscorner.diabetique.Doctor
import com.cscorner.diabetique.Doctor_Auth
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
    private lateinit var doctorId: String
    private lateinit var patientId: String



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
                            // Récupérer l'ID du patient
                            patientId = data.child("id").getValue(String::class.java).toString()
                            val doctorEmail = data.child("doctor").getValue(String::class.java)
                            // Si l'email du docteur est récupéré avec succès
                            doctorEmail?.let { doctorEmail ->
                                // Maintenant, récupérer les détails du docteur en utilisant son email
                                databaseReference.child("doctors").orderByChild("email")
                                    .equalTo(doctorEmail)
                                    .addListenerForSingleValueEvent(object : ValueEventListener {
                                        override fun onDataChange(doctorSnapshot: DataSnapshot) {
                                            for (doctorData in doctorSnapshot.children) {
                                                val doctorName = doctorData.child("fullName")
                                                    .getValue(String::class.java)
                                                val doctorEmail = doctorData.child("email")
                                                    .getValue(String::class.java)
                                                val doctorPhoneNumber =
                                                    doctorData.child("phoneNumber")
                                                        .getValue(String::class.java)
                                                val doctorAddress = doctorData.child("addresse")
                                                    .getValue(String::class.java)
                                                doctorId = doctorData.key.toString()
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
                                Log.e(
                                    "Doctor Fragment",
                                    "Email du docteur non trouvé pour le patient: $patientEmail"
                                )
                            }
                        }
                    }


                    override fun onCancelled(error: DatabaseError) {
                        // Gérer l'erreur lors de la récupération du champ doctor du patient
                    }
                })

chat.setOnClickListener{
    checkConversationExistence(patientId,doctorId)
}
        }

        return view


    }
    private fun checkConversationExistence(patientId: String, doctorId: String) {
        // Référence à la base de données pour les conversations
        val conversationRef = FirebaseDatabase.getInstance().reference.child("conversations")

        // Vérifier si une conversation existe déjà pour ce patient avec ce médecin
        conversationRef.orderByChild("patientId").equalTo(patientId)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    var conversationExists = false
                    var conversationId: String? = null
                    for (childSnapshot in snapshot.children) {
                        val conversation = childSnapshot.getValue(Conversation::class.java)
                        if (conversation?.doctorId == doctorId) {
                            // La conversation existe déjà pour ce patient avec ce médecin
                            conversationExists = true
                            conversationId = childSnapshot.key
                            break
                        }
                    }
                    if (conversationExists && conversationId != null) {
                        // Rediriger le patient vers la conversation existante
                        redirectToConversation(conversationId, doctorId)
                    } else {
                        // Créer une nouvelle conversation pour ce patient avec ce médecin
                        createNewConversation(doctorId, patientId)
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.e(TAG, "Erreur lors de la vérification de l'existence de la conversation: $error")
                    // Gérer l'erreur
                }
            })
    }

    private fun createNewConversation(doctorId: String, patientId: String) {
        // Obtenez une référence à la base de données Firebase
        val databaseReference = FirebaseDatabase.getInstance().reference

        // Créez une nouvelle conversation avec l'ID du patient comme clé
        val newConversationId = databaseReference.child("conversations").push().key ?: ""

        // Créez une instance de Conversation avec l'ID de la conversation, l'ID du patient et l'ID du médecin
        val conversation = Conversation(newConversationId, patientId,doctorId)

        // Enregistrez la nouvelle conversation dans la base de données sous un nouveau nœud
        databaseReference.child("conversations").child(newConversationId).setValue(conversation)
            .addOnSuccessListener {
                // Redirigez l'utilisateur vers la conversation avec ce patient
                redirectToConversation(newConversationId,doctorId)
            }
            .addOnFailureListener { e ->
                Log.e(ContentValues.TAG, "Erreur lors de la création de la conversation : $e")
                // Gérer l'échec de la création de la conversation
            }


    }

    private fun redirectToConversation(conversationId: String, doctorId: String) {
        val intent = Intent(requireContext(), Chat2Activity::class.java)
        intent.putExtra("conversationId", conversationId)
        intent.putExtra("doctorId", doctorId) // Passer l'ID du patient à ChatActivity
        startActivity(intent)

    }


}
