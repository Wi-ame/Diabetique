package com.cscorner.diabetique.pat_fragment

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cscorner.diabetique.R
import com.cscorner.diabetique.adapter.MessagesAdapter
import com.cscorner.diabetique.models.Message
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class Chat2Activity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var messagesAdapter: MessagesAdapter
    private lateinit var Id: String
    private lateinit var status: TextView
    private lateinit var  sendMessageButton: ImageView
    private lateinit var  messageBox: EditText
    private lateinit var senderId :String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat2)

        val toolbarNameTextView: TextView = findViewById(R.id.name) // Référence au TextView dans la Toolbar
        Id = intent.getStringExtra("doctorId") ?: ""
        Log.d("doctorID", "ID du med récupéré avec succès : $Id")

        val conversationId = intent.getStringExtra("conversationId")
        recyclerView = findViewById(R.id.recyclerView)
        status = findViewById(R.id.status)
        sendMessageButton = findViewById(R.id.id_send)
        messageBox = findViewById(R.id.messageBox)
        senderId = ""

        messagesAdapter = MessagesAdapter(senderId)
        recyclerView.adapter = messagesAdapter
        recyclerView.layoutManager = LinearLayoutManager(this)
        if (conversationId != null) {
            val conversationRef = FirebaseDatabase.getInstance().reference.child("conversations").child(conversationId)
            conversationRef.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        val patientId = snapshot.child("patientId").getValue(String::class.java)
                        if (patientId != null) {
                            senderId = patientId
                            Log.d("ChatActivity2", "ID du patient récupéré : $patientId")
                            // Update MessagesAdapter with the correct senderId
                            messagesAdapter = MessagesAdapter(senderId)
                            recyclerView.adapter = messagesAdapter
                        } else {
                            Log.e("ChatActivity2", "ID du patient non trouvé pour la conversation : $conversationId")
                        }
                    } else {
                        Log.e("ChatActivity2", "Aucune donnée trouvée pour l'ID de la conversation : $conversationId")
                    }
                }
                override fun onCancelled(error: DatabaseError) {
                    Log.e("ChatActivity2", "Erreur lors de la récupération de la conversation : $error")
                }
            })
        } else {
            Log.e("ChatActivity2", "ID de la conversation est null")
        }
        retrieveDoctorName(Id) { DoctorName ->
            toolbarNameTextView.text = DoctorName
        }
        retrieveDoctorStatus(Id){ Status->
            status.text = Status

        }
        if (conversationId != null) {
            fetchSentMessages(conversationId)
        }
        sendMessageButton.setOnClickListener {
            val messageText = messageBox.text.toString().trim()
            if (messageText.isNotEmpty()) {
                val senderId = senderId // Vous devez remplacer ceci par l'ID de l'utilisateur actuel
                val recipientId = Id // Utilisez l'ID du patient comme destinataire
                if (conversationId != null) {
                    sendMessageToDoctor(messageText,conversationId, recipientId,senderId)
                }

            } else {
                Toast.makeText(this, "Veuillez saisir un message", Toast.LENGTH_SHORT).show()
            }
        }


    }

    private fun retrieveDoctorName(doctorId: String?, callback: (String?) -> Unit) {
        if (doctorId != null) {
            val doctorsRef = FirebaseDatabase.getInstance().reference.child("doctors")
            val query = doctorsRef.orderByKey().equalTo(doctorId)
            query.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        // Si l'ID du docteur est trouvé, récupérez son nom complet
                        val doctorData = snapshot.children.firstOrNull()
                        val doctorName = doctorData?.child("fullName")?.getValue(String::class.java)
                        if (!doctorName.isNullOrEmpty()) {
                            Log.d("Firebase", "Nom du docteur récupéré : $doctorName")
                            callback(doctorName)
                        } else {
                            Log.e("Firebase", "Aucun nom complet trouvé pour l'ID du docteur : $doctorId")
                            callback(null)
                        }
                    } else {
                        Log.e("Firebase", "Aucune donnée trouvée pour l'ID du docteur : $doctorId")
                        callback(null)
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.e("Firebase", "Erreur lors de la récupération du nom du docteur : $error")
                    // Gérer l'erreur
                    callback(null)
                }
            })
        } else {
            Log.e("Firebase", "L'ID du docteur est null")
            callback(null)
        }
    }
    private fun retrieveDoctorStatus(doctorId: String?, callback: (String?) -> Unit) {
        if (doctorId != null) {
            val doctorsRef = FirebaseDatabase.getInstance().reference.child("doctors")
            val query = doctorsRef.orderByKey().equalTo(doctorId)
            query.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        // Si l'ID du docteur est trouvé, récupérez son nom complet
                        val doctorData = snapshot.children.firstOrNull()
                        val doctorStatus = doctorData?.child("statut")?.getValue(String::class.java)
                        if (!doctorStatus.isNullOrEmpty()) {
                            Log.d("Firebase", "statut du docteur récupéré : $doctorStatus")
                            callback(doctorStatus)
                        } else {
                            Log.e("Firebase", "Aucun statut trouvé pour l'ID du docteur : $doctorId")
                            callback(null)
                        }
                    } else {
                        Log.e("Firebase", "Aucune donnée trouvée pour l'ID du docteur : $doctorId")
                        callback(null)
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.e("Firebase", "Erreur lors de la récupération du nom du docteur : $error")
                    // Gérer l'erreur
                    callback(null)
                }
            })
        } else {
            Log.e("Firebase", "L'ID du docteur est null")
            callback(null)
        }
    }
    private fun sendMessageToDoctor(messageText: String,conversationId:String,patientId:String,doctorId:String) {
        val messagesRef = FirebaseDatabase.getInstance().reference
            .child("messages")
            .child(conversationId)

        val messageId = messagesRef.push().key ?: return // Generate unique ID for the message
        val message = Message(messageId, conversationId, doctorId, patientId, messageText, System.currentTimeMillis())
        messagesRef.child(messageId).setValue(message)
            .addOnSuccessListener {
                Toast.makeText(this, "Message envoyé", Toast.LENGTH_SHORT).show()
                messageBox.text.clear()
            }
            .addOnFailureListener { e ->
                Log.e("ChatActivity", "Erreur lors de l'envoi du message : ${e.message}")
                Toast.makeText(this, "Erreur lors de l'envoi du message", Toast.LENGTH_SHORT).show()
            }
    }
    private fun fetchSentMessages(conversationId:String) {
        val messagesRef = FirebaseDatabase.getInstance().reference
            .child("messages")
            .child(conversationId)
        messagesRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val sentMessages = mutableListOf<Message>()

                for (messageSnapshot in snapshot.children) {
                    val message = messageSnapshot.getValue(Message::class.java)
                    message?.let { sentMessages.add(it) }
                }

                // Sort messages by timestamp
                sentMessages.sortBy { it.timestamp }

                // Update adapter with sent messages
                messagesAdapter.submitList(sentMessages)
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("ChatActivity2", "Error fetching sent messages: $error")
            }
        })
    }

}