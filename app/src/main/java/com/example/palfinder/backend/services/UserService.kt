package com.example.palfinder.backend.services

import com.amplifyframework.api.ApiException
import com.amplifyframework.api.graphql.GraphQLResponse
import com.amplifyframework.api.graphql.PaginatedResult
import com.amplifyframework.api.graphql.model.ModelMutation
import com.amplifyframework.api.graphql.model.ModelQuery
import com.amplifyframework.core.Amplify
import com.amplifyframework.core.Consumer
import com.amplifyframework.datastore.generated.model.User
import com.amplifyframework.datastore.generated.model.UserStatus

object UserService {
    fun createIncompleteUser(
        username: String,
        email: String,
        onResponse: Consumer<GraphQLResponse<User>>,
        onError: Consumer<ApiException>
    ) {
        val user: User =
            User.builder().email(email).username(username).name("")
                .lastName("").status(UserStatus.INCOMPLETE).build()
        Amplify.API.mutate(
            ModelMutation.create(user),
            onResponse,
            onError
        )
    }

    fun getUserByUsername(
        username: String,
        onResponse: Consumer<GraphQLResponse<PaginatedResult<User>>>,
        onError: Consumer<ApiException>
    ) {
        Amplify.API.query(
            ModelQuery.list(User::class.java, User.USERNAME.contains(username)),
            onResponse,
            onError
        )
    }
}