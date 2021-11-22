package com.example.palfinder.backend.services

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.amplifyframework.datastore.generated.model.User

// a singleton to hold user data (this is a ViewModel pattern, without inheriting from ViewModel)
object UserData {

    private const val TAG = "UserData"

    private val _isSignedIn = MutableLiveData<Boolean>()
    var isSignedIn: LiveData<Boolean> = _isSignedIn
    private val _currentUser = MutableLiveData<User>()
    var currentUser: LiveData<User> = _currentUser

    fun setSignedIn(newValue : Boolean) {
        // use postvalue() to make the assignation on the main (UI) thread
        _isSignedIn.postValue(newValue)
    }

    fun setCurrentUser(user : User) {
        // use postvalue() to make the assignation on the main (UI) thread
        _currentUser.postValue(user)
    }

}