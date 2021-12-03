package com.example.palfinder.views.groups

import com.example.palfinder.backend.services.GroupAdmin

interface OnViewProfileListener {
    fun onClickViewProfile(data: GroupAdmin.GroupModel?)
}