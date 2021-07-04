package com.example.sendemailactivity.ui.login

/**
 * Authentication result : success (user details) or error message.
 */
data class SendEmailResult(
    val success: ReceivingUserView? = null,
    val error: Int? = null
)
