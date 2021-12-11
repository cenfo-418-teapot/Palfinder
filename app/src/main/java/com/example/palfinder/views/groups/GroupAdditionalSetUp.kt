package com.example.palfinder.views.groups

import androidx.lifecycle.MutableLiveData
import com.example.palfinder.backend.services.GroupAdmin

object GroupAdditionalSetUp {
    private const val TAG = "InitialSetupData"

    private val _tagsList = MutableLiveData<List<String>>()
//    private val _groupsList = MutableLiveData<List<GroupAdmin.GroupModel>>()
    private val _tmpGroup = MutableLiveData<GroupAdmin.GroupModel>()
    val tagsList = _tagsList
//    val groupsList = _groupsList
    val tmpGroup = _tmpGroup

    fun setTagsList(data: List<String>) {
        _tagsList.postValue(data)
    }

    fun removeTag(text: String?) {
        val newList = this._tagsList.value?.toMutableList()
        if (newList?.contains(text) == true) {
            newList.remove(text)
            _tagsList.postValue(newList)
        }
    }

    fun addTag(text: String?) {
        val newList = this._tagsList.value?.toMutableList() ?: mutableListOf()
        if (!newList.contains(text) && text != null) {
            newList.add(text)
            _tagsList.postValue(newList)
        }
    }

    fun setGroup(group: GroupAdmin.GroupModel?) {
        _tmpGroup.postValue(group)
    }

//    fun setGroupsList(data: List<GroupAdmin.GroupModel>) {
//        _groupsList.postValue(data)
//    }
}
