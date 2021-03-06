package com.example.palfinder.views.auth

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.navigation.Navigation
import com.amplifyframework.auth.AuthException
import com.example.palfinder.R
import com.example.palfinder.backend.services.AuthenticationService
import kotlinx.android.synthetic.main.activity_log_in.*
import kotlinx.android.synthetic.main.fragment_sign_up.*
import kotlinx.android.synthetic.main.fragment_sign_up.view.*

class SignUpFragment : Fragment() {
    private lateinit var progressBar: ProgressBar
    private val _uniqueUsername = MutableLiveData<Boolean>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _uniqueUsername.observe(this, {
            tilUsername?.error = if (!it) "That username is taken" else null
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = layoutInflater.inflate(R.layout.fragment_sign_up, container, false)
        progressBar = requireActivity().progressbar
        view.btnCancel?.setOnClickListener {
            progressBar.visibility = View.INVISIBLE
            Navigation.findNavController(view)
                .navigate(R.id.action_signUpFragment_to_signInFragment)
        }
        view.btnSignUp?.setOnClickListener {
            progressBar.visibility = View.VISIBLE
            try {
                val user = validForm()
                AuthenticationService.signUp(user.username, user.email, user.password, {
                    Log.i(TAG, "User: $user was registered, result: ${it.user}")
                    gotoConfirmSignUp(view)
                }) {
                    progressBar.visibility = View.INVISIBLE
                    when (it) {
                        is AuthException.UsernameExistsException -> _uniqueUsername.postValue(false)
                        else -> Log.e(TAG, "$user failed to register", it)
                    }
                }
            } catch (e: IllegalStateException) {
                progressBar.visibility = View.INVISIBLE
                Log.e(TAG, "Form Validation Failed", e)
            }
        }
        view.tvConfirmNewAccount?.setOnClickListener {
            gotoConfirmSignUp(view)
        }
        return view
    }

    private fun gotoConfirmSignUp(view: View) {
        Navigation.findNavController(view)
            .navigate(R.id.action_signUpFragment_to_confirmSignUpFragment)
    }

    private fun validForm(): UserForm {
        val user = etUsername?.text.toString()
        val email = etEmail?.text.toString()
        val password = etPassword?.text.toString()
        val confirmPassword = etConfirmPassword?.text.toString()

        val requiredErrorMsg = "This field is required"
        resetInputErrorMsg()
        user.ifBlank { tilUsername?.error = requiredErrorMsg }
        email.ifBlank { tilEmail?.error = requiredErrorMsg }
        password.ifBlank { tilPassword?.error = requiredErrorMsg }
        confirmPassword.ifBlank { tilConfirmPassword?.error = requiredErrorMsg }
        val equalPasswords = password == confirmPassword
        if (!equalPasswords) {
            tilConfirmPassword?.error = "Passwords do not match"
        }
        check(user.isNotBlank()) { "User is blank" }
        check(email.isNotBlank()) { "email is blank" }
        check(password.isNotBlank()) { "password is blank" }
        check(confirmPassword.isNotBlank()) { "Confirmation is blank" }
        check(equalPasswords) { "Passwords are not the same" }
        check(password.length >= 8) { "Password criteria was not met" }
        return UserForm(user, email, password)
    }

    private fun resetInputErrorMsg() {
        tilUsername?.error = null
        tilEmail?.error = null
        tilPassword?.error = null
        tilConfirmPassword?.error = null
    }

    private data class UserForm(val username: String, val email: String, val password: String)

    companion object {
        private const val TAG = "SignUpFragment"
    }
}
