package com.example.palfinder.views.user

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.amplifyframework.api.graphql.model.ModelQuery
import com.amplifyframework.core.Amplify
import com.amplifyframework.datastore.generated.model.User
import com.example.palfinder.R
import com.example.palfinder.backend.services.UserData
import com.example.palfinder.views.HomeActivity
import kotlinx.android.synthetic.main.activity_initial_account_setup.*

class InitialAccountSetup : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_initial_account_setup)
        val uid = intent.getStringExtra("uid")
        tvId.text = uid
        Amplify.API.query(
            ModelQuery.get(User::class.java, uid!!),
            {
                UserData.setCurrentUser(it.data)
            },
            { Log.e(TAG, "Query failed", it) }
        )
        btnCancel.setOnClickListener { startActivity(Intent(this, HomeActivity::class.java)) }
    }

    companion object {
        private const val TAG = "InitialAccountSetup"
    }
}