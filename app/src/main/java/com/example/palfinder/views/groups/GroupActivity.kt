package com.example.palfinder.views.groups

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.palfinder.R

class GroupActivity : AppCompatActivity(R.layout.activity_group_activity) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
    }
}