package com.example.sendemailactivity.ui.login

import android.app.Activity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast

import com.example.sendemailactivity.R

class SendEmailActivity : AppCompatActivity() {

    private lateinit var sendEmailViewModel: SendEmailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.send_email)

        val email = findViewById<EditText>(R.id.Email)
        val subject = findViewById<EditText>(R.id.subjects)
        val sendEmail = findViewById<Button>(R.id.sendEmail)
        val loading = findViewById<ProgressBar>(R.id.loading)

        sendEmailViewModel = ViewModelProvider(this, SendEmailViewModelFactory())
                .get(SendEmailViewModel::class.java)

        sendEmailViewModel.sendEmailFormState.observe(this@SendEmailActivity, Observer {
            val sendEmailState = it ?: return@Observer

            // disable login button unless both username / password is valid
            sendEmail.isEnabled = sendEmailState.isDataValid

            if (sendEmailState.emailError != null) {
                email.error = getString(sendEmailState.emailError)
            }
        })

        sendEmailViewModel.sendEmailResult.observe(this@SendEmailActivity, Observer {
            val sendEmailResult = it ?: return@Observer

            loading.visibility = View.GONE
            if (sendEmailResult.error != null) {
                showLoginFailed(sendEmailResult.error)
            }
            if (sendEmailResult.success != null) {
                updateUiWithUser(sendEmailResult.success)
            }
            setResult(Activity.RESULT_OK)

            //Complete and destroy login activity once successful
            finish()
        })

        email.afterTextChanged {
            sendEmailViewModel.sendEmailDataChanged(
                    email.text.toString(),
                    email.text.toString()
            )
        }

        subject.apply {
            afterTextChanged {
                sendEmailViewModel.sendEmailDataChanged(
                        email.text.toString(),
                        email.text.toString()
                )
            }

            setOnEditorActionListener { _, actionId, _ ->
                when (actionId) {
                    EditorInfo.IME_ACTION_DONE ->
                        sendEmailViewModel.sendEmail(
                                email.text.toString(),
                                subject.text.toString()
                        )
                }
                false
            }

            sendEmail.setOnClickListener {
                loading.visibility = View.VISIBLE
                sendEmailViewModel.sendEmail(email.text.toString(), subject.text.toString())
            }
        }
    }

    private fun updateUiWithUser(model: ReceivingUserView) {
        val sendMessage = "Email will be sent to "
        val email = model.email
        val subject = model.subject
        // TODO : initiate successful logged in experience
        Toast.makeText(
                applicationContext,
                "$sendMessage $email about $subject",
                Toast.LENGTH_LONG
        ).show()
    }

    private fun showLoginFailed(@StringRes errorString: Int) {
        Toast.makeText(applicationContext, errorString, Toast.LENGTH_SHORT).show()
    }
}

/**
 * Extension function to simplify setting an afterTextChanged action to EditText components.
 */
fun EditText.afterTextChanged(afterTextChanged: (String) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(editable: Editable?) {
            afterTextChanged.invoke(editable.toString())
        }

        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
    })
}
