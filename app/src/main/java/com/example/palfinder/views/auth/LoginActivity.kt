package com.example.palfinder.views.auth

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.palfinder.R

class LoginActivity : AppCompatActivity(R.layout.activity_log_in) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
    }
}