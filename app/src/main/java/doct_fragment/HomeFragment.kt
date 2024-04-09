package doct_fragment

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cscorner.diabetique.ChatActivity
import com.cscorner.diabetique.Doctor
import com.cscorner.diabetique.Patient
import com.cscorner.diabetique.R
import com.cscorner.diabetique.models.Conversation
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.Query
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase
class HomeFragment : Fragment() {

    private lateinit var adapter: PatientAdapter
    private val databaseReference = FirebaseDatabase.getInstance().reference.child("patients")
    private lateinit var editTextSearch: EditText
    private lateinit var currentDoctorId: String

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        editTextSearch = view.findViewById(R.id.editTextSearch)

        // Configurez votre RecyclerView
        val recyclerView: RecyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        val dividerItemDecoration = DividerItemDecoration(requireContext())
        recyclerView.addItemDecoration(dividerItemDecoration)

        // Récupérer l'e-mail du médecin actuellement connecté
        val doctorEmail = FirebaseAuth.getInstance().currentUser?.email ?: ""
        val currentUser = FirebaseAuth.getInstance().currentUser
        currentDoctorId = currentUser?.uid ?: ""
        Log.d("DoctorEmail", "Email du medecin : $doctorEmail")

        // Construire les options de configuration pour votre adaptateur FirebaseRecyclerAdapter
        val options = FirebaseRecyclerOptions.Builder<Patient>()
            .setQuery(getQueryForDoctorPatients(doctorEmail), Patient::class.java)
            .build()

        // Initialisez votre adaptateur avec les options configurées
        adapter = PatientAdapter(options, object : PatientAdapter.OnItemClickListener {
            override fun onItemClick(patient: Patient) {

                showPatientDetailsDialog(patient)
            }


            override fun onContactClick(patient: Patient) {
                currentDoctorId = currentUser?.uid ?: ""
                checkConversationExistence(patient, currentDoctorId)
            }
        })

        // Définir l'adaptateur pour le RecyclerView
        recyclerView.adapter = adapter
        editTextSearch.addTextChangedListener { text ->
            val searchText = text.toString().trim()
            if (doctorEmail.isNotEmpty()) {
               filterByName(searchText)
                // Filtrer par email du médec

            }
        }

        return view
    }

    override fun onStart() {
        super.onStart()
        adapter.startListening()
    }
    override fun onStop() {
        super.onStop()
        adapter.stopListening()
    }

    private fun getQueryForDoctorPatients(doctorEmail: String): Query {
        // Créez une requête pour récupérer les patients associés à ce médecin (en utilisant l'e-mail)
        return databaseReference.orderByChild("doctor").equalTo(doctorEmail)
    }








    fun filterByName(name: String) {
        val query = databaseReference.orderByChild("fullName")
            .startAt(name).endAt(name + "\uf8ff")

        val options = FirebaseRecyclerOptions.Builder<Patient>()
            .setQuery(query, Patient::class.java)
            .build()

        adapter.updateOptions(options)
    }

    private fun showPatientDetailsDialog(patient: Patient) {
        val dialogView = LayoutInflater.from(requireContext()).inflate(R.layout.dialogue_details, null)

        val fullNameTextView: TextView = dialogView.findViewById(R.id.full_name_text_view)
        val ageTextView: TextView = dialogView.findViewById(R.id.age_text_view)
        val diabetesTypeTextView: TextView = dialogView.findViewById(R.id.diabetes_type_text_view)
        val emailTextView: TextView = dialogView.findViewById(R.id.email_text_view)
        val phoneNumberTextView: TextView = dialogView.findViewById(R.id.phone_number_text_view)
        val gender: TextView = dialogView.findViewById(R.id.gender_text_view)
        val activite: TextView=dialogView.findViewById(R.id.activite_text_view)
        val poids: TextView=dialogView.findViewById(R.id.poids_text_view)
        val taille: TextView=dialogView.findViewById(R.id.taille_text_view)
        val allergie: TextView=dialogView.findViewById(R.id.allergie_text_view)
        fullNameTextView.text = patient.fullName
        ageTextView.text = "Age: ${patient.age}"
        diabetesTypeTextView.text = "Type de diabète: ${patient.diabete}"
        emailTextView.text = "Email: ${patient.email}"
        phoneNumberTextView.text = "Numéro de téléphone: ${patient.phoneNumber}"
        gender.text="gender:${patient.gender}"
        activite.text="Activité physique:${patient.activite}"
        poids.text="poids:${patient.poids}"
        taille.text="taille:${patient.taille}"
        allergie.text="Allergie:${patient.allergie}"
        AlertDialog.Builder(requireContext())
            .setView(dialogView)
            .setTitle("Détails du patient")
            .setPositiveButton("Fermer", null)
            .show()
    }
    private fun checkConversationExistence(patient: Patient,doctorId: String) { // Correction 1: Ajout de la virgule pour séparer les paramètres
        // Référence à la base de données pour les conversations
        val conversationRef = FirebaseDatabase.getInstance().reference.child("conversations")

        // Vérifier si une conversation existe déjà entre le médecin et le patient
        conversationRef.orderByChild("patientId").equalTo(patient.id)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    var conversationExists = false
                    var conversationId: String? = null
                    for (childSnapshot in snapshot.children) {
                        val conversation = childSnapshot.getValue(Conversation::class.java)
                        if (conversation?.doctorId == currentDoctorId) {
                            conversationExists = true
                            conversationId = childSnapshot.key
                            break
                        }
                    }
                    if (conversationExists && conversationId != null) {
                        redirectToConversation(conversationId,patient.id)
                    } else {
                        createNewConversation(patient, doctorId) // Correction 2: Ajout du paramètre 'doctor'
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.e(TAG, "Erreur lors de la vérification de l'existence de la conversation: $error")
                    // Gérer l'erreur
                }
            })
    }

    private fun createNewConversation(patient: Patient, doctorId: String) {
        // Obtenez une référence à la base de données Firebase
        val databaseReference = FirebaseDatabase.getInstance().reference

        // Créez une nouvelle conversation avec l'ID du patient comme clé
        val newConversationId = databaseReference.child("conversations").push().key ?: ""

        // Créez une instance de Conversation avec l'ID de la conversation, l'ID du patient et l'ID du médecin
        val conversation = Conversation(newConversationId, patient.id, doctorId)

        // Enregistrez la nouvelle conversation dans la base de données sous un nouveau nœud
        databaseReference.child("conversations").child(newConversationId).setValue(conversation)
            .addOnSuccessListener {
                // Redirigez l'utilisateur vers la conversation avec ce patient
                redirectToConversation(newConversationId,patient.id)
            }
            .addOnFailureListener { e ->
                Log.e(TAG, "Erreur lors de la création de la conversation : $e")
                // Gérer l'échec de la création de la conversation
            }
    }

    private fun redirectToConversation(conversationId: String, patientId: String) {
        val intent = Intent(requireContext(), ChatActivity::class.java)
        intent.putExtra("conversationId", conversationId)
        intent.putExtra("patientId", patientId) // Passer l'ID du patient à ChatActivity
        startActivity(intent)
    }



}



