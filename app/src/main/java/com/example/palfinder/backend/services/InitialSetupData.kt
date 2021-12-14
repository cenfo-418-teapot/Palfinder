package com.example.palfinder.backend.services

import androidx.lifecycle.MutableLiveData
import com.amplifyframework.datastore.generated.model.User

object InitialSetupData {
    private const val TAG = "InitialSetupData"

    private val _tagsList = MutableLiveData<List<String>>()
    private val _groupsList = MutableLiveData<List<GroupAdmin.GroupModel>>()
    private val _allGroupsList = MutableLiveData<List<GroupAdmin.GroupModel>>()
    private val _userInfo = MutableLiveData<User?>()
    val userInfo = _userInfo
    val tagsList = _tagsList
    val groupsList = _groupsList
    val allGroupsList = _allGroupsList

    fun setUser(user: User?) {
        _userInfo.postValue(user)
    }

    fun setAllGroupsList(data: List<GroupAdmin.GroupModel>) {
        _allGroupsList.postValue(data)
    }

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

    fun removeGroup(text: String?) {
        val newList = this._groupsList.value?.toMutableList()
        if (newList?.stream()?.anyMatch { group -> group.name == text} == true) {
            newList.remove(newList.first { group -> group.name == text })
            _groupsList.postValue(newList)
        }
    }

    fun addGroup(group: GroupAdmin.GroupModel?) {
        val newList = this._groupsList.value?.toMutableList() ?: mutableListOf()
        if (!newList.contains(group) && group != null) {
            newList.add(group)
            _groupsList.postValue(newList)
        }
    }

    fun setGroupsList(data: List<GroupAdmin.GroupModel>) {
        _groupsList.postValue(data)
    }
}