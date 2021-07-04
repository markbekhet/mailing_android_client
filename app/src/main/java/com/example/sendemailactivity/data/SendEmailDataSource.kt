package com.example.sendemailactivity.data

import com.example.sendemailactivity.data.model.ReceivingUser
import com.example.sendemailactivity.data.model.SendEmailHandler
import java.io.IOException

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
class SendEmailDataSource{
    fun sendEmail(email: String, subject: String): Result<ReceivingUser> {
        try {
            // TODO: handle loggedInUser authentication
            SendEmailHandler(email, subject).start()
            val receivingUser = ReceivingUser(email, subject)
            return Result.Success(receivingUser)
        } catch (e: Throwable) {
            print(e.message)
            print(e.cause)
            return Result.Error(IOException("Error logging in", e))
        }
    }

    fun logout() {
        // TODO: revoke authentication
    }
}
