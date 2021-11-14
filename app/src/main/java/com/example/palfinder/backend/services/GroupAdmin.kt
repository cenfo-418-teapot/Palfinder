package com.example.palfinder.backend.services

import android.graphics.Bitmap
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

object GroupAdmin {
    private const val TAG = "UserData"

//    private var name: String = ""
//    private var tags: ArrayList<String> = arrayListOf()
//    private var description: String = ""
//    private var eventsIds: ArrayList<Int> = arrayListOf()
//    private var memberIds: ArrayList<Int> = arrayListOf()

    fun createGroup (
        id: String,
        imageName: String?,
        name: String,
        description: String,
        tags: ArrayList<String>,
        eventIds: ArrayList<Int>,
        memberIds: ArrayList<Int>
    ) : Group
    {
        return Group(id,name,description,tags,eventIds,memberIds,imageName)
    }

    private val _groups = MutableLiveData<MutableList<Group>>(mutableListOf())

    private fun <T> MutableLiveData<T>.notifyObserver() {
        this.postValue(this.value)
    }
    fun notifyObserver() {
        this._groups.notifyObserver()
    }

    fun groups() : LiveData<MutableList<Group>> = _groups
    fun addGroup(n : Group) {
        val notes = _groups.value
        if (notes != null) {
            notes.add(n)
            _groups.notifyObserver()
        } else {
            Log.e(TAG, "addNote : group collection is null !!")
        }
    }
    fun deleteGroup(at: Int) : Group?  {
        val Group = _groups.value?.removeAt(at)
        _groups.notifyObserver()
        return Group
    }

    fun resetGroups() {
        this._groups.value?.clear()  //used when signing out
        _groups.notifyObserver()
    }

    data class Group(
        val id: String,
        val name: String,
        val description: String,
        val tags: ArrayList<String>,
        val eventIds: ArrayList<Int>,
        val memberIds: ArrayList<Int>,
        var imageName: String? = null)
    {
        override fun toString(): String = name

        // bitmap image
        var image : Bitmap? = null
        // return an API NoteData from this Group object
        val data : GroupData
            get() = GroupData.builder()
                .id(this.id)
                .name(this.name)
                .description(this.description)
                .tags(this.tags)// TODO: cambiar tipo de dato
                .events(this.eventIds)
                .image(this.imageName)
                .id(this.id)
                .build()

        // static function to create a Group from a NoteData API object
        companion object {
            fun from(groupData : GroupData) : Group {
                val result = Group(groupData.id, groupData.name, groupData.description, groupData.image)

                if (groupData.image != null) {
                    GroupService.retrieveImage(groupData.image!!) {
                        result.image = it

                        // force a UI update
                        with(UserData) { notifyObserver() }
                    }
                }
                return result
            }
        }
    }
}