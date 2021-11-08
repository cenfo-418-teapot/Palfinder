package com.example.palfinder.views.auth

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.example.palfinder.R
import com.example.palfinder.backend.services.AuthenticationService
import kotlinx.android.synthetic.main.sign_up_fragment.*
import kotlinx.android.synthetic.main.sign_up_fragment.view.*

class SignUpFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = layoutInflater.inflate(R.layout.sign_up_fragment, container, false)
        view.btnCancel?.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.from_signUp_to_signIn)
        }
        view.btnSignUp?.setOnClickListener {
            try {
                val user = validForm()
                AuthenticationService.signUp(user.username, user.email, user.password, {
                    Log.i(TAG, "User: $user was registered, result: ${it.user}")
                    gotoConfirmSignUp(view)
                }, {
                    Log.e(TAG, "$user failed to register", it)
                })
            } catch (e: IllegalStateException) {
                Log.e(TAG, "Form Validation Failed", e)
            }
        }
        view.tvConfirmNewAccount?.setOnClickListener {
            gotoConfirmSignUp(view)
        }
        return view
    }

    private fun gotoConfirmSignUp(view: View) {
        Navigation.findNavController(view).navigate(R.id.from_signUp_to_confirmSignUp)
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
