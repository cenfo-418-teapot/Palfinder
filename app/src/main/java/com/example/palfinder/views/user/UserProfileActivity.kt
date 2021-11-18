package com.example.palfinder.views.user

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.palfinder.R

class UserProfileActivity : AppCompatActivity(R.layout.activity_user_profile) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
    }
}