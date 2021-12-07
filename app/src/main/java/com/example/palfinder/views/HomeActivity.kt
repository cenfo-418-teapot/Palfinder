package com.example.palfinder.views

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import com.example.palfinder.R
import com.example.palfinder.backend.services.AuthenticationService
import com.example.palfinder.backend.services.UserData
import com.example.palfinder.databinding.FragmentUserProfileDetailBinding
import com.example.palfinder.views.auth.LoginActivity
import com.example.palfinder.views.events.EventActivity
import com.example.palfinder.views.groups.GroupActivity
import com.example.palfinder.views.tag.TagFormDemoActivity
import com.example.palfinder.views.user.SearchUserDemoActivity
import com.example.palfinder.views.user.SearchUserFragment
import com.example.palfinder.views.user.UserProfileActivity
import com.example.palfinder.views.user.UserProfileDetailFragment
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity(R.layout.activity_home) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setupBottomNavigation(applicationContext)
        UserData.isSignedIn.observe(this, {
            if (!it) {
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
            }
        })
    }

    private fun setupBottomNavigation(ctx: Context) {
        bottom_navigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.mnuHome -> {
                    fragmentContainerView.findNavController().setGraph(R.navigation.groups_navigation)
                    true
                }
                R.id.mnuSearch -> {
                    fragmentContainerView.findNavController().setGraph(R.navigation.search_navigation)
                    true
                }
                R.id.mnuEvents -> {
                    startActivity(Intent(ctx, EventActivity::class.java))
                    true
                }
                R.id.mnuProfile -> {
                    fragmentContainerView.findNavController().setGraph(R.navigation.user_navigation)
                    true
                }
                else -> false
            }
        }
    }

    companion object {
        private const val TAG = "HomeActivity"
    }
}