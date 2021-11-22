package com.example.palfinder.views

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.amplifyframework.core.Amplify
import com.amplifyframework.datastore.generated.model.UserStatus
import com.example.palfinder.R
import com.example.palfinder.backend.services.UserService
import com.example.palfinder.views.auth.LoginActivity
import com.example.palfinder.views.user.InitialAccountSetup

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.activity_main)

        Handler(mainLooper).postDelayed({
            if (Amplify.Auth.currentUser == null) {
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
            } else {
                UserService.getUserByUsername(Amplify.Auth.currentUser.username,
                    {
                        //        If it doesn't, create a new object
                        val items = it.data.items as ArrayList
                        if (items.stream().findFirst().get().status == UserStatus.INCOMPLETE) {
                            val uid = items.stream().findFirst().get().id
                            val i = Intent(this, InitialAccountSetup::class.java)
                            i.putExtra("uid", uid)
                            startActivity(i)
                        } else startActivity(Intent(this, HomeActivity::class.java))
                        finish()
                    }, { Log.e(TAG, "Failed to create user list in dynamo", it) }
                )
            }
        }, 300)
    }

    private fun onUserLogin() {
//        First see if the user exists in dynamo
        UserService.getUserByUsername(Amplify.Auth.currentUser.username,
            {
//        If it doesn't, create a new object
                val items = it.data.items as ArrayList
                when {
                    items.size == 0 -> {
                        Log.i(TAG, "First Login from the user, will register in the DB")
                        Amplify.Auth.fetchUserAttributes(
                            { attrs ->
                                val email =
                                    attrs.find { value -> value.key.keyString == "email" }?.value
                                createUserProfile(email ?: "unknown")
                            },
                            { error -> Log.e(TAG, "Failed to get user attributes", error) }
                        )
                    }
                    items.stream().findFirst().get().status == UserStatus.INCOMPLETE -> {
                        val uid = items.stream().findFirst().get().id
                        val i = Intent(this, InitialAccountSetup::class.java)
                        i.putExtra("uid", uid)
                        startActivity(i)
                        finish()
                    }
                    else -> startActivity(Intent(this, HomeActivity::class.java))
                }
            },
            { Log.e(TAG, "Failed to get user by id", it) }
        )
    }


    private fun createUserProfile(email: String) {
        val currentUser = Amplify.Auth.currentUser
        UserService.createIncompleteUser(currentUser.username, email,
            {
                val i = Intent(this, InitialAccountSetup::class.java)
                i.putExtra("uid", it.data.id)
                startActivity(i)
                finish()
            }
        ) { Log.e(TAG, "Failed to create user in dynamo", it) }
    }

    companion object {
        private const val TAG = "MainActivity"
    }

}
