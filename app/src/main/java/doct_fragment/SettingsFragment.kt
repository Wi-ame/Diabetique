package doct_fragment

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.cscorner.diabetique.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class SettingsFragment : Fragment() {

    private lateinit var editTextName: EditText
    private lateinit var editTextEmail: EditText
    private lateinit var editTextPassword: EditText
    private lateinit var editTextPhoneNumber: EditText
    private lateinit var editTextAddress: EditText
    private lateinit var buttonSave: Button
    private lateinit var databaseReference: DatabaseReference
    private lateinit var currentUserUid: String
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_settings, container, false)

        // Initialise les vues
        editTextName = view.findViewById(R.id.editTextName)
        editTextEmail = view.findViewById(R.id.editTextEmail)
        editTextPassword = view.findViewById(R.id.editTextPassword)
        editTextPhoneNumber = view.findViewById(R.id.editTextPhoneNumber)
        editTextAddress = view.findViewById(R.id.editTextAddress)
        buttonSave = view.findViewById(R.id.buttonSave)

        // Configure les écouteurs d'événements
        buttonSave.setOnClickListener {
            saveChanges()
        }

        return view
    }

    private fun saveChanges() {
        // Récupère les valeurs saisies par l'utilisateur
        val newName = editTextName.text.toString()
        val newEmail = editTextEmail.text.toString()
        val newPassword = editTextPassword.text.toString()
        val newPhoneNumber = editTextPhoneNumber.text.toString()
        val newAddress = editTextAddress.text.toString()

        // Récupère la référence à la base de données
        val databaseReference = FirebaseDatabase.getInstance().getReference("doctors")
        val currentUserUid = FirebaseAuth.getInstance().currentUser?.uid ?: ""

        // Met à jour les informations du médecin dans la base de données
        val doctorRef = databaseReference.child(currentUserUid)
        doctorRef.child("fullName").setValue(newName)
        doctorRef.child("email").setValue(newEmail)
        doctorRef.child("phoneNumber").setValue(newPhoneNumber)
        doctorRef.child("addresse").setValue(newAddress)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Met à jour l'email si changé
                    val user = FirebaseAuth.getInstance().currentUser
                    if (user?.email != newEmail) {
                        user?.updateEmail(newEmail)?.addOnCompleteListener { emailUpdateTask ->
                            if (emailUpdateTask.isSuccessful) {
                                Log.d(TAG, "Email updated successfully")
                                // Met à jour le mot de passe si changé
                                if (newPassword.isNotEmpty()) {
                                    user.updatePassword(newPassword).addOnCompleteListener { passwordUpdateTask ->
                                        if (passwordUpdateTask.isSuccessful) {
                                            Log.d(TAG, "Password updated successfully")
                                            // Affiche un message de confirmation en cas de succès
                                            Toast.makeText(
                                                requireContext(),
                                                "Modifications enregistrées avec succès",
                                                Toast.LENGTH_SHORT
                                            ).show()
                                        } else {
                                            Log.e(TAG, "Failed to update password: ${passwordUpdateTask.exception}")
                                            // Affiche un message d'erreur en cas d'échec de la mise à jour du mot de passe
                                            Toast.makeText(
                                                requireContext(),
                                                "Erreur lors de la mise à jour du mot de passe",
                                                Toast.LENGTH_SHORT
                                            ).show()
                                        }
                                    }
                                } else {
                                    Log.d(TAG, "No password change requested")
                                    // Affiche un message de confirmation en cas de succès
                                    Toast.makeText(
                                        requireContext(),
                                        "Modifications enregistrées avec succès",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            } else {
                                Log.e(TAG, "Failed to update email: ${emailUpdateTask.exception}")
                                // Affiche un message d'erreur en cas d'échec de la mise à jour de l'email
                                Toast.makeText(
                                    requireContext(),
                                    "Erreur lors de la mise à jour de l'email",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    } else {
                        Log.d(TAG, "No email change requested")
                        // Affiche un message de confirmation en cas de succès
                        Toast.makeText(
                            requireContext(),
                            "Modifications enregistrées avec succès",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                } else {
                    // Affiche un message d'erreur en cas d'échec de la mise à jour
                    Toast.makeText(
                        requireContext(),
                        "Erreur lors de la mise à jour des informations",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }
}
