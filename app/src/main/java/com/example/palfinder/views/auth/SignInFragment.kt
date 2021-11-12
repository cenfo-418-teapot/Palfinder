package com.example.palfinder.views.auth

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.example.palfinder.R
import com.example.palfinder.backend.services.AuthenticationService
import com.example.palfinder.backend.services.UserData
import com.example.palfinder.views.HomeActivity
import com.example.palfinder.views.auth.recover.password.RecoverPasswordActivity
import kotlinx.android.synthetic.main.fragment_sign_in.*
import kotlinx.android.synthetic.main.fragment_sign_in.view.*

class SignInFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = layoutInflater.inflate(R.layout.fragment_sign_in, container, false)
        view.btnSignUp?.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_signInFragment_to_signUpFragment)
        }
        view.btnSignIn?.setOnClickListener {
            signIn()
        }
        UserData.isSignedIn.observe(viewLifecycleOwner) {
            if (it) {
                startActivity(Intent(activity, HomeActivity::class.java))
            } else {
                tvErrorMsg.visibility = View.VISIBLE
                tvErrorMsg.text = resources.getString(R.string.wrong_login_credentials)
            }
        }
        view.tvRecoverPassword?.setOnClickListener {
            startActivity(Intent(context, RecoverPasswordActivity::class.java))
        }
        return view
    }

    override fun onResume() {
        super.onResume()
        tvErrorMsg?.visibility = View.INVISIBLE
    }

    private fun signIn() {
        val username = etUsername.text.toString()
        val password = etPassword.text.toString()
        AuthenticationService.signIn(username, password,
            { result ->
                val status = result.isSignInComplete
                if (status) {
                    Log.i("AuthQuickstart", "Sign in succeeded")
                } else {
                    Log.i("AuthQuickstart", "Sign in not complete")
                }
                UserData.setSignedIn(status)
            },
            {
                Log.e("AuthQuickstart", "Failed to sign in", it)
                UserData.setSignedIn(false)
            }
        )

    }
}
