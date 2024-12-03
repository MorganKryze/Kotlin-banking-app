package fr.kodelab.banking.model

data class User(
    val id: Long,
    val name: String,
    val pin: String,
    val lastLogin: String?,
    val creationDate: String?
)
