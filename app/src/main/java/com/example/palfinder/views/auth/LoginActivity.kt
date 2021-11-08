package com.example.palfinder.views.auth

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.palfinder.R

class LoginActivity : AppCompatActivity(R.layout.activity_log_in) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
    }
}