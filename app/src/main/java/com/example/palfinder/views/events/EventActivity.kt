package com.example.palfinder.views.events

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.palfinder.R

class EventActivity : AppCompatActivity(R.layout.activity_event) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
    }
}