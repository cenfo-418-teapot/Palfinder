package com.example.palfinder.backend.services

import android.util.Log
import com.amplifyframework.auth.AuthException
import com.amplifyframework.auth.AuthUserAttributeKey
import com.amplifyframework.auth.options.AuthSignUpOptions
import com.amplifyframework.auth.result.AuthResetPasswordResult
import com.amplifyframework.auth.result.AuthSignInResult
import com.amplifyframework.auth.result.AuthSignUpResult
import com.amplifyframework.core.Action
import com.amplifyframework.core.Amplify
import com.amplifyframework.core.Consumer

object AuthenticationService {
    private const val TAG = "AuthenticationService"

    fun signOut(onSuccess: Action, onError: Consumer<AuthException>) {
        Log.i(TAG, "Initiate Sign Out Sequence")
        Amplify.Auth.signOut(onSuccess, onError)
    }

    fun signIn(
        user: String,
        password: String,
        onSuccess: (AuthSignInResult) -> Unit,
        onError: Consumer<AuthException>,
    ) {
        Log.i(TAG, "Initiate Sign In Sequence")
        Amplify.Auth.signIn(user, password, onSuccess, onError)
    }

    fun signUp(
        user: String,
        email: String,
        password: String,
        onSuccess: Consumer<AuthSignUpResult>,
        onError: Consumer<AuthException>,
    ) {
        val options = AuthSignUpOptions.builder()
            .userAttribute(AuthUserAttributeKey.email(), email)
            .build()
        Amplify.Auth.signUp(user, password, options, onSuccess, onError)
    }

    fun confirmSignUp(
        username: String, code: String,
        onSuccess: Consumer<AuthSignUpResult>,
        onError: Consumer<AuthException>,
    ) {
        Amplify.Auth.confirmSignUp(username, code, onSuccess, onError)
    }


    object PasswordReset {
        fun sendCode(
            username: String,
            onSuccess: Consumer<AuthResetPasswordResult>,
            onError: Consumer<AuthException>,
        ) {
            Amplify.Auth.resetPassword(username, onSuccess, onError)
        }

        fun confirm(
            newPassword: String,
            code: String,
            onSuccess: Action,
            onError: Consumer<AuthException>
        ) {
            Amplify.Auth.confirmResetPassword(newPassword, code, onSuccess, onError)
        }
    }
}
