package com.example.palfinder.views

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.forEach
import androidx.navigation.findNavController
import com.example.palfinder.R
import com.example.palfinder.backend.services.UserData
import com.example.palfinder.components.OnIdListChange
import com.example.palfinder.views.auth.LoginActivity
import com.example.palfinder.views.events.EventActivity
import com.example.palfinder.views.groups.GroupAdditionalSetUp
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.fragment_initial_tag_selection.*

class HomeActivity : AppCompatActivity(R.layout.activity_home), OnIdListChange {
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

    override fun onIdListChange(list: List<String>) {
        GroupAdditionalSetUp.setTagsList(list)
    }

    private fun setupBottomNavigation(ctx: Context) {
        toggleNavigationButton(R.id.mnuHome)
        bottom_navigation.setOnItemSelectedListener { item ->
            toggleNavigationButton(item.itemId)
            when (item.itemId) {
                R.id.mnuHome -> {
                    fragmentContainerView.findNavController()
                        .setGraph(R.navigation.groups_navigation)
                    true
                }
                R.id.mnuSearch -> {
                    fragmentContainerView.findNavController()
                        .setGraph(R.navigation.search_navigation)
                    true
                }
                R.id.mnuEvents -> {
                    fragmentContainerView.findNavController()
                        .setGraph(R.navigation.events_navigation)
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

    private fun toggleNavigationButton(id: Int) {
        bottom_navigation.menu.forEach {
            it.isEnabled = it.itemId != id
        }
    }

    companion object {
        private const val TAG = "HomeActivity"
    }
}