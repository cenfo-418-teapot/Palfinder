package com.example.palfinder.backend.services

import android.graphics.Bitmap
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.amplifyframework.datastore.generated.model.*

object GroupAdmin {
    private const val TAG = "GroupAdmin"

    private val _groups = MutableLiveData<MutableList<GroupModel>>(mutableListOf())

    private fun <T> MutableLiveData<T>.notifyObserver() {
        this.postValue(this.value)
    }
    fun notifyObserver() {
        this._groups.notifyObserver()
    }

    fun groups() : LiveData<MutableList<GroupModel>> = _groups
    fun addGroup(n : GroupModel) {
        val tmpGroups = _groups.value
        if (tmpGroups != null) {
            tmpGroups.add(n)
            _groups.notifyObserver()
        } else {
            Log.e(TAG, "addGroup : group collection is null !!")
        }
    }
    fun deleteGroup(at: Int) : GroupModel?  {
        val group = _groups.value?.removeAt(at)
        _groups.notifyObserver()
        return group
    }

    fun resetGroups() {
        this._groups.value?.clear()  //used when signing out
        _groups.notifyObserver()
    }

    data class GroupModel(
        val id: String,
        val name: String,
        val description: String,
        val tags: List<TagGroup>,
        val events: List<Event>,
        val users: List<GroupMembers>,
        val state: State,
        var imageName: String? = null)
    {
        override fun toString(): String = name

        // bitmap image
        var image : Bitmap? = null
        // return an API GroupData from this Group object
        val data : Group
            get() = Group.builder()
                .name(this.name)
                .description(this.description)
                .image(this.imageName)
                .state(this.state)
                .id(this.id)
                .build()

        // static function to create a Group from a GroupData API object
        companion object {
            fun from(groupData : Group) : GroupModel {
                val result = GroupModel(
                    groupData.id,
                    groupData.name,
                    groupData.description,
                    groupData.tags,
                    groupData.events,
                    groupData.users,
                    groupData.state,
                    groupData.image)

                if (groupData.image != null) {
                    GroupService.retrieveImage(groupData.image!!) {
                        result.image = it

                        // force a UI update
                        with(GroupAdmin) { notifyObserver() }
                    }
                }
                return result
            }
        }
    }
}