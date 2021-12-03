package com.example.palfinder.views.user.account

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class InitialSetupViewModel: ViewModel() {
    val tagsList: MutableLiveData<List<String>> by lazy {
        MutableLiveData<List<String>>()
    }
    val groupsList: MutableLiveData<List<String>> by lazy {
        MutableLiveData<List<String>>()
    }
}