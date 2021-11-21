package com.example.palfinder.views.user

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.palfinder.R
import kotlinx.android.synthetic.main.activity_initial_account_setup.*

class InitialAccountSetup : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_initial_account_setup)
        val uid = intent.getStringExtra("uid")
        tvId.text = uid
    }
}