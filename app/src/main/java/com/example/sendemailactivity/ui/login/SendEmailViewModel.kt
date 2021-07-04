package com.example.sendemailactivity.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import android.util.Patterns
import com.example.sendemailactivity.data.SendEmailRepository
import com.example.sendemailactivity.data.Result

import com.example.sendemailactivity.R

class SendEmailViewModel(private val sendEmailRepository: SendEmailRepository) : ViewModel() {

    private val _sendEmailForm = MutableLiveData<SendEmailFormState>()
    val sendEmailFormState: LiveData<SendEmailFormState> = _sendEmailForm

    private val _sendEmailResult = MutableLiveData<SendEmailResult>()
    val sendEmailResult: LiveData<SendEmailResult> = _sendEmailResult

    fun sendEmail(username: String, subject: String) {
        // can be launched in a separate asynchronous job
        val result = sendEmailRepository.sendEmail(username, subject)

        if (result is Result.Success) {
            _sendEmailResult.value = SendEmailResult(success = ReceivingUserView(email = result.data.userId, subject = result.data.subject))
        } else {
            _sendEmailResult.value = SendEmailResult(error = R.string.login_failed)
        }
    }

    fun sendEmailDataChanged(email: String, subject: String) {
        if (!isEmailValid(email)) {
            _sendEmailForm.value = SendEmailFormState(emailError = R.string.invalid_username)
        } else if (!isSubjectValid(subject)) {
            _sendEmailForm.value = SendEmailFormState()
        } else {
            _sendEmailForm.value = SendEmailFormState(isDataValid = true)
        }
    }

    // A placeholder username validation check
    private fun isEmailValid(email: String): Boolean {
        return if (email.contains('@')) {
            Patterns.EMAIL_ADDRESS.matcher(email).matches()
        } else {
            email.isNotBlank()
        }
    }

    // A placeholder password validation check
    private fun isSubjectValid(subject: String): Boolean {
        return subject.length > 1
    }
}
