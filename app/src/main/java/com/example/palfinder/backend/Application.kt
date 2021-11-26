package com.example.palfinder.backend

import android.app.Application
import android.util.Log
import com.amplifyframework.AmplifyException
import com.amplifyframework.api.aws.AWSApiPlugin
import com.amplifyframework.auth.cognito.AWSCognitoAuthPlugin
import com.amplifyframework.core.Amplify
import com.amplifyframework.storage.s3.AWSS3StoragePlugin
import com.example.palfinder.backend.strapi.Strapi

class ApplicationInitializer : Application() {
    override fun onCreate() {
        super.onCreate()
        try {
            Amplify.addPlugin(AWSCognitoAuthPlugin())
            Amplify.addPlugin(AWSApiPlugin())
            Amplify.addPlugin(AWSS3StoragePlugin())
            Amplify.configure(applicationContext)
            Log.i(TAG, "Initialized Amplify")
            Strapi.getSingleType(applicationContext, "general")
        } catch (error: AmplifyException) {
            Log.e(TAG, "Could not initialize Amplify", error)
        }
    }
    companion object {
        private const val TAG = "Application Initializer"
    }
}