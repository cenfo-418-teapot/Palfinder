package com.example.palfinder.views.auth.recover.password

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.palfinder.R
import com.example.palfinder.backend.services.AuthenticationService
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_validate_recovery_code.*
import kotlinx.android.synthetic.main.fragment_validate_recovery_code.view.*

class ValidateRecoveryCodeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_validate_recovery_code, container, false)
        view.btnSaveChanges?.setOnClickListener {
            try {
                val (code, password) = validForm()
                AuthenticationService.PasswordReset.confirm(password, code,
                    {
                        activity?.finish()
                    },
                    {
                        Log.e("AuthQuickstart", "Failed to confirm password reset", it)
                        Snackbar.make(view, "Invalid code, try requesting a new one", Snackbar.LENGTH_SHORT).show()
                    }
                )
            } catch (e: IllegalStateException) {
                Log.e(TAG, "Form Validation Failed", e)
            }
        }
        return view
    }

    private fun validForm(): Pair<String, String> {
        val code = etSecurityCode?.text.toString()
        val password = etPassword?.text.toString()
        val confirmPassword = etConfirmPassword?.text.toString()

        val requiredErrorMsg = "This field is required"
        resetInputErrorMsg()
        code.ifBlank { tilPassword?.error = requiredErrorMsg }
        password.ifBlank { tilPassword?.error = requiredErrorMsg }
        confirmPassword.ifBlank { tilConfirmPassword?.error = requiredErrorMsg }
        val equalPasswords = password == confirmPassword
        if (!equalPasswords) {
            tilConfirmPassword?.error = "Passwords do not match"
        }
        check(code.isNotBlank()) { "Security code is blank" }
        check(password.isNotBlank()) { "Password is blank" }
        check(confirmPassword.isNotBlank()) { "Confirmation is blank" }
        check(equalPasswords) { "Passwords are not the same" }
        check(password.length >= 8) { "Password criteria was not met" }
        return Pair(code, password)
    }

    private fun resetInputErrorMsg() {
        tilSecurityCode?.error = null
        tilPassword?.error = null
        tilConfirmPassword?.error = null
    }

    companion object {
        private const val TAG = "ValidateRecoveryCodeFragment"
    }
}
