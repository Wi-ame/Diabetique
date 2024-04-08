package com.cscorner.diabetique.models
data class Message(
    val messageId: String, // Identifiant unique du message
    val senderId: String, // Identifiant unique de l'expéditeur du message
    val recipientId:String,
    val text: String, // Texte du message
    val timestamp: Long // Timestamp du message (pour le tri et l'affichage)
)
