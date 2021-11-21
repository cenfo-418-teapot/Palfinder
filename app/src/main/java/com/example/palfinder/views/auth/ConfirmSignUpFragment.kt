package com.example.palfinder.views.auth

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.navigation.Navigation
import com.amplifyframework.auth.AuthException
import com.example.palfinder.R
import com.example.palfinder.backend.services.AuthenticationService
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_confirm_sign_up.*
import kotlinx.android.synthetic.main.fragment_confirm_sign_up.view.*

class ConfirmSignUpFragment : Fragment() {
    private val _isConfirmed = MutableLiveData<Pair<Boolean, String?>>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_confirm_sign_up, container, false)
        view.btnCancel?.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_confirmSignUpFragment_to_signUpFragment)
        }
        view.btnConfirm?.setOnClickListener {
            try {
                val (username, securityCode) = validateForm()
                AuthenticationService.confirmSignUp(username, securityCode,
                    { result ->
                        if (result.isSignUpComplete) {
                            val msg = "Confirm signUp succeeded"
                            Log.i("AuthQuickstart", msg)
                            _isConfirmed.postValue(Pair(true, msg))
                        } else {
                            val msg = "Confirm sign up not complete"
                            Log.i("AuthQuickstart", msg)
                            _isConfirmed.postValue(Pair(false, msg))
                        }
                    }
                ) { error ->
                    var msg = "Failed to confirm sign up"
                    when(error) {
                        is AuthException.NotAuthorizedException -> msg = "The code was already used."
                    }
                    Log.e("AuthQuickstart", msg, error)
                    _isConfirmed.postValue(Pair(false, msg))
                }
            } catch (e: IllegalStateException) {
                _isConfirmed.postValue(Pair(false, null))
            }
        }
        _isConfirmed.observe(viewLifecycleOwner) {
            if (it.first) Navigation.findNavController(view).navigate(R.id.action_confirmSignUpFragment_to_signInFragment)
            else if (it.second?.isNotBlank() == true) {
                Snackbar.make(view, it.second!!, Snackbar.LENGTH_SHORT).show()
            }
        }
        return view
    }

    private fun validateForm(): Pair<String, String> {
        val username = etUsername?.text.toString()
        val securityCode = etSecurityCode?.text.toString()
        val requiredErrorMsg = "This field is required"
        resetInputErrorMsg()
        username.ifBlank { tilUsername?.error = requiredErrorMsg }
        securityCode.ifBlank { tilSecurityCode?.error = requiredErrorMsg }
        check(username.isNotBlank())
        check(securityCode.isNotBlank())
        return Pair(username, securityCode)
    }

    private fun resetInputErrorMsg() {
        tilUsername?.error = null
        tilSecurityCode?.error = null
    }

    companion object {
        private const val TAG = "ConfirmSignUpFragment"
    }
}