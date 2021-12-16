package com.example.palfinder.views.groups

import com.amplifyframework.datastore.generated.model.GroupMembers

interface OnShowProfileListener {
    fun onUnJoinGroup(data: GroupMembers?)
}
