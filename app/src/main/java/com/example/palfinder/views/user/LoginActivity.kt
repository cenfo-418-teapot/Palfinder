package com.example.palfinder.views.user

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.amplifyframework.auth.AuthUserAttributeKey
import com.amplifyframework.auth.options.AuthSignUpOptions
import com.amplifyframework.core.Amplify
import com.example.palfinder.R
import com.example.palfinder.UserData
import com.example.palfinder.backend.services.AuthenticationService
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.activity_login)

        btnRegister.setOnClickListener {
            val options = AuthSignUpOptions.builder()
                .userAttribute(AuthUserAttributeKey.email(), "s.m.sebastian.n@gmail.com")
                .build()
            Amplify.Auth.signUp("demo_sebastian", "Password123", options,
                { Log.i("AuthQuickStart", "Sign up succeeded: $it") },
                { Log.e ("AuthQuickStart", "Sign up failed", it) }
            )
//           AuthenticationService.signUp("demo", "dev.snsm@gmail.com", "$12test12",
//                {
//                    Log.d(TAG, "Created a new user")
//                },
//                {
//                    Log.d(TAG, "Failed to create a new user")
//               })
        }
        btnContinue.setOnClickListener {
            try {
                val (user, password) = validForm()
                AuthenticationService.signIn(user, password,
                    {
                        UserData.setSignedIn(true)
                    },
                    { error ->
                        UserData.setSignedIn(false)
                    }
                )
            } catch (e: IllegalStateException) {
                Log.e(TAG, "Form Failed", e)
            }
        }

        UserData.isSignedIn.observe(this, {
            findViewById<TextView>(R.id.tvResult).text =
                if (it) "Signed In" else "Failed to sign in"
        })
    }

    private fun validForm(): Pair<String, String> {
        val user = etUsername.text.toString()
        val password = etPassword.text.toString()
        val requiredErrorMsg = "This field is required"
        tilUsername.error = null
        tilPassword.error = null
        user.ifBlank { tilUsername.error = requiredErrorMsg }
        password.ifBlank { tilPassword.error = requiredErrorMsg }
        check(user.isNotBlank())
        check(password.isNotBlank())
        return Pair(user, password)
    }

    companion object {
        private const val TAG = "LoginActivity"
    }
}