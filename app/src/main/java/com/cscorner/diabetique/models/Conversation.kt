package com.cscorner.diabetique.models

data class Conversation(
    val conversationId: String = "",
    val patientId: String = "",
    val doctorId: String = "",
    val messagesSent: List<Message> = mutableListOf(),
    val messagesReceived: List<Message> = mutableListOf()
)

