package com.example.sendemailactivity.data.model

/**
 * Data class that captures user information for logged in users retrieved from LoginRepository
 */
data class ReceivingUser(
        val userId: String,
        val subject: String
)
