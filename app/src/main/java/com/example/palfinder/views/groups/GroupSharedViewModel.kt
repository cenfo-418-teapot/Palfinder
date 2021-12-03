package com.example.palfinder.views.groups

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.palfinder.backend.services.GroupAdmin

class GroupSharedViewModel : ViewModel() {
    val message = MutableLiveData<GroupAdmin.GroupModel>()

    fun sendMessage(model: GroupAdmin.GroupModel) {
        message.value = model
    }
}
