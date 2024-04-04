package doct_fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import com.cscorner.diabetique.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class ProfileFragment : Fragment() {

    private lateinit var fullNameTextView: TextView
    private lateinit var emailTextView: TextView
    private lateinit var passwordTextView: TextView
    private lateinit var phoneNumberTextView: TextView
    private lateinit var addressTextView: TextView

    private val databaseReference = FirebaseDatabase.getInstance().reference
    private val currentUser = FirebaseAuth.getInstance().currentUser
    private lateinit var settingsTextView: TextView


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_profile, container, false)

        // Initialize views
        fullNameTextView = view.findViewById(R.id.textViewName)
        emailTextView = view.findViewById(R.id.textViewEmail)
        passwordTextView = view.findViewById(R.id.textViewPassword)
        phoneNumberTextView = view.findViewById(R.id.textViewPhoneNumber)
        addressTextView = view.findViewById(R.id.textViewAddress)
        settingsTextView = view.findViewById(R.id.textViewSettings)
        settingsTextView.setOnClickListener {
            findNavController().navigate(R.id.action_profileFragment_to_settingsFragment)

        }
        // Retrieve and display doctor's information
        currentUser?.email?.let { email ->
            databaseReference.child("doctors").orderByChild("email").equalTo(email)
                .addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        for (data in snapshot.children) {
                            val fullName = data.child("fullName").getValue(String::class.java)
                            val password = data.child("password").getValue(String::class.java)
                            val phoneNumber = data.child("phoneNumber").getValue(String::class.java)
                            val address = data.child("addresse").getValue(String::class.java)

                            fullNameTextView.text = fullName
                            emailTextView.text = email
                            passwordTextView.text = password
                            phoneNumberTextView.text = phoneNumber
                            addressTextView.text = address
                        }
                    }

                    override fun onCancelled(error: DatabaseError) {

                    }
                })
        }

        return view
    }
}
