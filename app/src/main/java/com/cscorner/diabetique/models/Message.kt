package com.cscorner.diabetique.models
data class Message(
    val messageId: String = "",
    val conversationId: String = "",
    val senderId: String = "",
    val recipientId: String = "",
    val text: String = "",
    val timestamp: Long = 0L
)

