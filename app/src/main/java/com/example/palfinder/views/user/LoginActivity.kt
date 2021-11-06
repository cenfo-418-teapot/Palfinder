package com.example.palfinder.views.user

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.palfinder.R
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.activity_log_in.*

class LoginActivity : AppCompatActivity(R.layout.activity_log_in) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
    }
}