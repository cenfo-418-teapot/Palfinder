package com.example.palfinder.views.groups

import com.amplifyframework.datastore.generated.model.GroupMembers
import com.example.palfinder.backend.services.GroupAdmin

interface OnViewProfileListener {
    fun onClickViewProfile(data: GroupAdmin.GroupModel?)
    fun onJoinGroup(data: GroupAdmin.GroupModel?)
    fun onUnJoinGroup(data: GroupMembers?)
}