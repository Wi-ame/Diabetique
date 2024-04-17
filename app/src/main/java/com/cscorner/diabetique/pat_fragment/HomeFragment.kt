package com.cscorner.diabetique.pat_fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import com.cscorner.diabetique.R
import com.cscorner.diabetique.models.GlycemieData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class HomeFragment : Fragment() {
    private lateinit var editTextGlycemiaBeforeMeal: EditText
    private lateinit var editTextGlycemiaAfterMeal: EditText
    private lateinit var checkBoxPhysicalActivity: CheckBox
    private lateinit var editTextCarbohydrateType: EditText
    private lateinit var buttonSubmit: Button
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var databaseReference: DatabaseReference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home2, container, false)

        editTextGlycemiaBeforeMeal = view.findViewById(R.id.editTextGlycemiaBeforeMeal)
        editTextGlycemiaAfterMeal = view.findViewById(R.id.editTextGlycemiaAfterMeal)
        checkBoxPhysicalActivity = view.findViewById(R.id.checkBoxPhysicalActivity)
        editTextCarbohydrateType = view.findViewById(R.id.editTextCarbohydrateType)
        buttonSubmit = view.findViewById(R.id.buttonSubmit)
        firebaseAuth = FirebaseAuth.getInstance()
        databaseReference = FirebaseDatabase.getInstance().reference
        buttonSubmit.setOnClickListener {
            saveGlycemiaData()
        }
        return view
    }
    private fun saveGlycemiaData() {
        val user: FirebaseUser? = firebaseAuth.currentUser
        val userId: String? = user?.uid

        // Obtention de l'ID du patient à partir de la base de données
        getUserIdFromDatabase { patientId ->
            // Vérification de la non-nullité de l'ID du patient
            userId?.let {
                val glycemiaBeforeMeal = editTextGlycemiaBeforeMeal.text.toString().toDoubleOrNull()
                val glycemiaAfterMeal = editTextGlycemiaAfterMeal.text.toString().toDoubleOrNull()
                val physicalActivity = checkBoxPhysicalActivity.isChecked
                val carbohydrateType = editTextCarbohydrateType.text.toString()

                // Création de l'objet GlycemieData avec l'ID du patient
                val glycemiaData = GlycemieData(
                    patientId, // Utilisation de l'ID du patient récupéré
                    glycemiaBeforeMeal,
                    glycemiaAfterMeal,
                    physicalActivity,
                    carbohydrateType
                )

                // Enregistrement des données dans la base de données
                databaseReference.child("patients").child(patientId).child("glycemiaData").push()
                    .setValue(glycemiaData)
                    .addOnSuccessListener {
                        // Enregistrement réussi
                        editTextGlycemiaBeforeMeal.text.clear()
                        editTextGlycemiaAfterMeal.text.clear()
                        checkBoxPhysicalActivity.isChecked = false
                        editTextCarbohydrateType.text.clear()
                    }
                    .addOnFailureListener { e ->
                        // Gérer l'échec de l'enregistrement
                    }
            }
        }
    }

    private fun getUserIdFromDatabase(callback: (String) -> Unit) {
        // Écoute des changements dans le nœud "patients"
        databaseReference.child("patients").addListenerForSingleValueEvent(object :
            ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                // Parcourir tous les enfants du nœud "patients"
                snapshot.children.forEach { patientSnapshot ->
                    // Récupérer l'ID du patient (clé du snapshot)
                    val patientId = patientSnapshot.key
                    // Appeler le callback avec l'ID du patient
                    if (patientId != null) {
                        callback(patientId)
                        return@forEach
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                // Gérer l'erreur
            }
        })
    }


}