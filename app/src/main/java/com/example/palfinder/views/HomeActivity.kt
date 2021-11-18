package com.example.palfinder.views

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.palfinder.R
import com.example.palfinder.backend.services.AuthenticationService
import com.example.palfinder.backend.services.UserData
import com.example.palfinder.views.auth.LoginActivity
import com.example.palfinder.views.events.EventActivity
import com.example.palfinder.views.user.UserProfileActivity
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity(R.layout.activity_home) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        btnLogout.setOnClickListener {
            AuthenticationService.signOut({
                UserData.setSignedIn(false)
            }) { error ->
                Log.e(TAG, "Failed to log out!", error)
            }
        }
        iv_user_profile.setOnClickListener{
            startActivity(Intent(this, UserProfileActivity::class.java))
        }
        iv_events.setOnClickListener{
            startActivity(Intent(this, EventActivity::class.java))
        }
        UserData.isSignedIn.observe(this, {
            if (!it) {
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
            }
        })
    }

    companion object {
        private const val TAG = "HomeActivity"
    }
}