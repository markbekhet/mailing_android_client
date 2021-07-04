package com.example.sendemailactivity.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.sendemailactivity.data.SendEmailDataSource
import com.example.sendemailactivity.data.SendEmailRepository

/**
 * ViewModel provider factory to instantiate LoginViewModel.
 * Required given LoginViewModel has a non-empty constructor
 */
class SendEmailViewModelFactory : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SendEmailViewModel::class.java)) {
            return SendEmailViewModel(
                    sendEmailRepository = SendEmailRepository(
                            dataSource = SendEmailDataSource()
                    )
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
