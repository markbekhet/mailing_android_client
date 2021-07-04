package com.example.sendemailactivity.ui.login

/**
 * User details post authentication that is exposed to the UI
 */
data class ReceivingUserView(
        val email: String,
        val subject: String
        //... other data fields that may be accessible to the UI
)
