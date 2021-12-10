package com.example.palfinder.views.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.amplifyframework.datastore.generated.model.Tag

object SearchData {
    private val _tags = MutableLiveData<List<Tag>>()
    var tags: LiveData<List<Tag>> = _tags
    private val _searchTerm = MutableLiveData<String>()
    var searchTerm: LiveData<String> = _searchTerm

    fun setSearchTerm(text: String) {
        _searchTerm.postValue(text)
    }

}