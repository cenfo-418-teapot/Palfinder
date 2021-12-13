package com.example.palfinder.backend.services

import android.graphics.Bitmap
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.amplifyframework.core.model.temporal.Temporal
import com.amplifyframework.datastore.generated.model.*

/**
 * Object for Groups administration.
 * @author Isaac Miranda
 */
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

    fun getGroup(at: Int): GroupModel? {
        return _groups.value?.get(at)
    }

    fun resetGroups() {
        this._groups.value?.clear()  //used when signing out
        _groups.notifyObserver()
    }

    data class GroupModel(
        val id: String,
        var name: String,
        var description: String,
        var tags: List<TagGroup>?,
        var events: List<Event>?,
        var users: List<GroupMembers>?,
        var status: Status,
        var imageName: String? = null,
        var createdOn: Temporal.DateTime? = null,
        var updatedOn: Temporal.DateTime? = null)
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
                .status(this.status)
                .id(this.id)
                .build()

        val dataUpdate : Group
            get() = Group.builder()
                .name(this.name)
                .description(this.description)
                .image(this.imageName)
                .status(this.status)
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
                    groupData.status,
                    groupData.image,
                    groupData.createdOn,
                    groupData.updatedOn)

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