package com.cscorner.diabetique

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cscorner.diabetique.adapter.MessagesAdapter
import com.cscorner.diabetique.models.Message
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class ChatActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var messagesAdapter: MessagesAdapter
    private lateinit var Id: String
    private lateinit var status:TextView
    private lateinit var  sendMessageButton:ImageView
    private lateinit var  messageBox:EditText
    private lateinit var senderId :String
    private lateinit var idconv:String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        val toolbarNameTextView: TextView =
            findViewById(R.id.name) // Référence au TextView dans la Toolbar
        Id = intent.getStringExtra("patientId") ?: ""// Assurez-vous que le bon layout est chargé ici
        if (Id != null) {
            Log.d("PatientID", "ID du patient récupéré avec succès : $Id")
        } else {
            Log.e(
                "PatientID",
                "Erreur : Impossible de récupérer l'ID du patient à partir de l'intent"
            )
        }


        val conversationId = intent.getStringExtra("conversationId")
        // Récupérer la référence du RecyclerView depuis le layout
        recyclerView = findViewById(R.id.recyclerView)
        status=findViewById(R.id.status)
        sendMessageButton =findViewById(R.id.id_send)
         messageBox = findViewById(R.id.messageBox)
        if (conversationId != null) {
            // Récupérer l'objet Conversation correspondant à l'ID de la conversation
            val conversationRef = FirebaseDatabase.getInstance().reference.child("conversations").child(conversationId)
            conversationRef.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        val doctorId = snapshot.child("doctorId").getValue(String::class.java)
                        if (doctorId != null) {
                             senderId=doctorId

                            Log.d("ChatActivity", "ID du médecin récupéré : $doctorId")
                        } else {
                            Log.e("ChatActivity", "ID du médecin non trouvé pour la conversation : $conversationId")
                        }
                    } else {
                        Log.e("ChatActivity", "Aucune donnée trouvée pour l'ID de la conversation : $conversationId")
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.e("ChatActivity", "Erreur lors de la récupération de la conversation : $error")
                }
            })
        } else {
            Log.e("ChatActivity", "ID de la conversation est null")
        }
        // Initialiser l'adaptateur de messages avec l'ID de l'utilisateur actuel
        messagesAdapter =
            MessagesAdapter("currentUserId") // Remplacez "currentUserId" par l'ID de l'utilisateur actuel
        val patientId = Id.toString().trim()
        // Définir l'orientation du RecyclerView (optionnel)
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Définir l'adaptateur de messages sur votre RecyclerView
        recyclerView.adapter = messagesAdapter



        retrievePatientName(patientId) { patientName ->
            toolbarNameTextView.text = patientName
        }
        retrievePatientStatus(patientId){ Status->
            status.text = Status

        }
        if(conversationId != null) {
            idconv=conversationId
        }
        sendMessageButton.setOnClickListener {
            val messageText = messageBox.text.toString().trim()
            if (messageText.isNotEmpty()) {
                val senderId = senderId // Vous devez remplacer ceci par l'ID de l'utilisateur actuel
                val recipientId = patientId // Utilisez l'ID du patient comme destinataire
                sendMessageToPatient(messageText, senderId, recipientId)
                fetchSentMessages(idconv)

            } else {
                Toast.makeText(this, "Veuillez saisir un message", Toast.LENGTH_SHORT).show()
            }
        }

        }

    private fun retrievePatientName(patientId: String?, callback: (String?) -> Unit) {
        if (patientId != null) {
            val patientsRef = FirebaseDatabase.getInstance().reference.child("patients")
            val query = patientsRef.orderByChild("id").equalTo(patientId)
            query.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        // Si l'ID du patient existe, récupérez le nom complet
                        for (patientSnapshot in snapshot.children) {
                            val fullName =
                                patientSnapshot.child("fullName").getValue(String::class.java)
                            if (!fullName.isNullOrEmpty()) {
                                Log.d("Firebase", "Nom du patient récupéré : $fullName")
                                callback(fullName)
                                return  // Sortir de la boucle une fois que le nom est trouvé
                            }
                        }
                        Log.e(
                            "Firebase",
                            "Aucun nom complet trouvé pour l'ID du patient : $patientId"
                        )
                        callback(null)
                    } else {
                        Log.e("Firebase", "Aucune donnée trouvée pour l'ID du patient : $patientId")
                        callback(null)
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.e("Firebase", "Erreur lors de la récupération du nom du patient : $error")
                    // Gérer l'erreur
                    callback(null)
                }
            })
        } else {
            Log.e("Firebase", "L'ID du patient est null")
            callback(null)
        }
    }

    private fun retrievePatientStatus(patientId: String?, callback: (String?) -> Unit) {
        if (patientId != null) {
            val patientsRef = FirebaseDatabase.getInstance().reference.child("patients")
            val query = patientsRef.orderByChild("id").equalTo(patientId)
            query.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        for (patientSnapshot in snapshot.children) {
                            val status = patientSnapshot.child("statut").getValue(String::class.java)
                            if (!status.isNullOrEmpty()) {
                                Log.d("Firebase", "Statut du patient récupéré : $status")
                                callback(status)
                                return
                            }
                        }
                        Log.e("Firebase", "Aucun statut trouvé pour l'ID du patient : $patientId")
                        callback(null)
                    } else {
                        Log.e("Firebase", "Aucune donnée trouvée pour l'ID du patient : $patientId")
                        callback(null)
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.e("Firebase", "Erreur lors de la récupération du statut du patient : $error")
                    callback(null)
                }
            })
        } else {
            Log.e("Firebase", "L'ID du patient est null")
            callback(null)
        }
    }
    private fun sendMessageToPatient(messageText: String, senderId: String, recipientId: String) {
        val messagesRef = FirebaseDatabase.getInstance().reference.child("messages")
        val messageId = messagesRef.push().key ?: return // Générer un ID unique pour le message
        val message = Message(messageId, senderId, recipientId, messageText, System.currentTimeMillis())
        messagesRef.child(messageId).setValue(message)
            .addOnSuccessListener {
                Toast.makeText(this, "Message envoyé", Toast.LENGTH_SHORT).show()
                messageBox.text.clear()
            }
            .addOnFailureListener { e ->
                Log.e("Firebase", "Erreur lors de l'envoi du message : ${e.message}")
                Toast.makeText(this, "Erreur lors de l'envoi du message", Toast.LENGTH_SHORT).show()
            }
    }
    private fun fetchSentMessages(conversationId: String) {
        val messagesRef = FirebaseDatabase.getInstance().reference.child("messages")
        val query = messagesRef.orderByChild("conversationId").equalTo(conversationId)

        query.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val sentMessages = mutableListOf<Message>()
                for (messageSnapshot in snapshot.children) {
                    val message = messageSnapshot.getValue(Message::class.java)
                    if (message != null && message.senderId == senderId) {
                        sentMessages.add(message)
                    }
                }
                // Mettez à jour votre adaptateur de messages avec les messages envoyés
                messagesAdapter.submitList(sentMessages)
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("Firebase", "Erreur lors de la récupération des messages envoyés : $error")
            }
        })
    }


    // Fonction pour envoyer un message au patient

    }




