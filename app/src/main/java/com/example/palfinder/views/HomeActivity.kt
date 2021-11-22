package com.example.palfinder.views

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.amplifyframework.api.graphql.model.ModelMutation
import com.amplifyframework.api.graphql.model.ModelQuery
import com.amplifyframework.core.Amplify
import com.amplifyframework.datastore.generated.model.Status
import com.amplifyframework.datastore.generated.model.User
import com.example.palfinder.R
import com.example.palfinder.backend.services.AuthenticationService
import com.example.palfinder.backend.services.UserData
import com.example.palfinder.views.auth.LoginActivity
import com.example.palfinder.views.events.EventActivity
import com.example.palfinder.views.tag.TagFormDemoActivity
import com.example.palfinder.views.user.InitialAccountSetup
import com.example.palfinder.views.user.SearchUserDemoActivity
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
        ivSearchUser.setOnClickListener {
            startActivity(Intent(this, SearchUserDemoActivity::class.java))
        }
        iv_user_profile.setOnClickListener {
            startActivity(Intent(this, UserProfileActivity::class.java))
        }
        iv_events.setOnClickListener {
            startActivity(Intent(this, EventActivity::class.java))
        }
        iv_demo_tags.setOnClickListener {
            startActivity(Intent(this, TagFormDemoActivity::class.java))
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