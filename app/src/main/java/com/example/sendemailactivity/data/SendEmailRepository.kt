package com.example.sendemailactivity.data

import com.example.sendemailactivity.data.model.ReceivingUser

/**
 * Class that requests authentication and user information from the remote data source and
 * maintains an in-memory cache of login status and user credentials information.
 */

class SendEmailRepository(val dataSource: SendEmailDataSource) {

    // in-memory cache of the loggedInUser object
    var user: ReceivingUser? = null
        private set

    val isLoggedIn: Boolean
        get() = user != null

    init {
        // If user credentials will be cached in local storage, it is recommended it be encrypted
        // @see https://developer.android.com/training/articles/keystore
        user = null
    }

    fun logout() {
        user = null
        dataSource.logout()
    }

    fun sendEmail(email: String, subject: String): Result<ReceivingUser> {
        // handle login
        val result = dataSource.sendEmail(email, subject)

        if (result is Result.Success) {
            setLoggedInUser(result.data)
        }

        return result
    }

    private fun setLoggedInUser(receivingUser: ReceivingUser) {
        this.user = receivingUser
        // If user credentials will be cached in local storage, it is recommended it be encrypted
        // @see https://developer.android.com/training/articles/keystore
    }
}
