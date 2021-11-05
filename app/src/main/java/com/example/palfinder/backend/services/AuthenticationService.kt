package com.example.palfinder.backend.services

import android.util.Log
import com.amplifyframework.auth.AuthException
import com.amplifyframework.auth.AuthUserAttributeKey
import com.amplifyframework.auth.options.AuthSignUpOptions
import com.amplifyframework.auth.result.AuthSignInResult
import com.amplifyframework.auth.result.AuthSignUpResult
import com.amplifyframework.core.Amplify

object AuthenticationService {
    private const val TAG = "AuthenticationService"

    fun signOut() {
        Log.i(TAG, "Initiate Sign Out Sequence")
/*
        Amplify.Auth.signOut(
            { Log.i(TAG, "Signed out!") },
            { error -> Log.e(TAG, "Sign Out failed", error) }
        )
*/
    }

    fun signIn(
        user: String,
        password: String,
        onSuccess: (AuthSignInResult) -> Unit,
        onError: (AuthException) -> Unit
    ) {
        Log.i(TAG, "Initiate Sign In Sequence")
        Amplify.Auth.signIn(user, password, onSuccess, onError)
    }

    fun signUp(
        user: String,
        email: String,
        password: String,
        onSuccess: (AuthSignUpResult) -> Unit,
        onError: (AuthException) -> Unit
    ) {
        val options = AuthSignUpOptions.builder()
            .userAttribute(AuthUserAttributeKey.email(), email)
            .build()
        Amplify.Auth.signUp(user, password, options, onSuccess, onError)
    }
}
