package com.example.palfinder.views.auth

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.amplifyframework.core.Amplify
import com.amplifyframework.datastore.generated.model.UserStatus
import com.example.palfinder.R
import com.example.palfinder.backend.services.AuthenticationService
import com.example.palfinder.backend.services.UserData
import com.example.palfinder.backend.services.UserService
import com.example.palfinder.views.HomeActivity
import com.example.palfinder.views.auth.recover.password.RecoverPasswordActivity
import com.example.palfinder.views.user.account.InitialAccountSetup
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
                // first login setup
                onUserLogin()
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
                    Log.i(TAG, "Sign in succeeded")
                } else {
                    Log.i(TAG, "Sign in not complete")
                }
                UserData.setSignedIn(status)
            },
            {
                Log.e(TAG, "Failed to sign in", it)
                UserData.setSignedIn(false)
            }
        )
    }

    private fun onUserLogin() {
//        First see if the user exists in dynamo
        UserService.getUserByUsername(Amplify.Auth.currentUser.username,
            {
//        If it doesn't, create a new object
                val items =it.data.items as ArrayList
                when {
                    items.size == 0 -> {
                        Log.i(TAG, "First Login from the user, will register in the DB")
                        Amplify.Auth.fetchUserAttributes(
                            { attrs ->
                                val email = attrs.find { value -> value.key.keyString == "email" }?.value
                                createUserProfile(email ?: "unknown")
                            },
                            { error -> Log.e(TAG, "Failed to get user attributes", error) }
                        )
                    }
                    items.stream().findFirst().get().status == UserStatus.INCOMPLETE -> {
                        val uid = items.stream().findFirst().get().id
                        val i = Intent(activity, InitialAccountSetup::class.java)
                        i.putExtra("uid", uid)
                        startActivity(i)
                        activity?.finish()
                    }
                    else -> startActivity(Intent(activity, HomeActivity::class.java))
                }
            },
            { Log.e(TAG, "Failed to get user by id", it) }
        )
    }

    private fun createUserProfile(email: String) {
        val currentUser = Amplify.Auth.currentUser
        UserService.createIncompleteUser(currentUser.username, email,
            {
                val i = Intent(activity, InitialAccountSetup::class.java)
                i.putExtra("uid", it.data.id)
                startActivity(i)
                activity?.finish()
            }
        ) { Log.e(TAG, "Failed to create user in dynamo", it) }
    }

    companion object {
        private const val TAG = "SignInFragment"
    }

}
