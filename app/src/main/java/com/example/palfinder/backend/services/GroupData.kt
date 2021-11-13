package com.example.palfinder.backend.services

object GroupData {
    private const val TAG = "UserData"

    private var name: String = ""
    private var tags: ArrayList<String> = arrayListOf()
    private var description: String = ""
    private var eventsIds: ArrayList<Int> = arrayListOf()
    private var memberIds: ArrayList<Int> = arrayListOf()

    fun createGroup(
        name: String = "",
        tags: ArrayList<String> = arrayListOf(),
        description: String = "",
        eventIds: ArrayList<Int> = arrayListOf(),
        memberIds: ArrayList<Int> = arrayListOf()
    )
    {
        this.name =  name
        this.tags =  tags
        this.description =  description
        this.eventsIds =  eventIds
        this.memberIds =  memberIds
    }
}