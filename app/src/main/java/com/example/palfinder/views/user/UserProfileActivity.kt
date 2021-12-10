package com.example.palfinder.views.user

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.amplifyframework.api.graphql.model.ModelQuery
import com.amplifyframework.core.Amplify
import com.amplifyframework.datastore.generated.model.User
import com.example.palfinder.R
import com.example.palfinder.backend.services.UserData

class UserProfileActivity : AppCompatActivity(R.layout.activity_user_profile) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        Amplify.API.query(
//            ModelQuery.list(User::class.java, User.ID.contains("first")),
//            { response ->
//                response.data.forEach { todo ->
//                    Log.i("MyAmplifyApp", todo.name)
//                }
//            },
//            { Log.e("MyAmplifyApp", "Query failure", it) }
//        )
    }
}