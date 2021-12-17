package com.example.palfinder.views.search

import android.util.Log
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.amplifyframework.api.graphql.model.ModelMutation
import com.amplifyframework.api.graphql.model.ModelQuery
import com.amplifyframework.core.Amplify
import com.amplifyframework.datastore.generated.model.Tag
import com.amplifyframework.datastore.generated.model.TagStatus

object SearchData {
    private const val TAG = "SearchData"
    private val _tags = MutableLiveData<List<Tag>?>()
    var tags: LiveData<List<Tag>?> = _tags
    private val _searchTerm = MutableLiveData<String>()
    var searchTerm: LiveData<String> = _searchTerm

    fun setSearchTerm(text: String) {
        _searchTerm.postValue(text)
    }

    fun setTags(list: List<String>, owner: LifecycleOwner) {
        if (list.isNotEmpty()) {
            val tagToCreate = MutableLiveData<String>()
            tagToCreate.observe(owner, { name ->
                val tag = Tag.builder().name(name).status(TagStatus.ALLOWED).uses(1).build()
                Amplify.API.mutate(ModelMutation.create(tag),
                    {
                        postSingleTag(it.data)
                    },
                    { Log.e(TAG, "Failed to create tag $name", it) })
            })
            var tmpList = _tags.value ?: listOf()
            // si hay tags pero la lista de nombres es menor a los valores actuales
            if (!tmpList.isNullOrEmpty() && list.size < tmpList.size) tmpList = listOf()
            _tags.postValue(tmpList)
            list.forEach { name ->
                if (tmpList.find { tag -> tag.name == name } == null) {
                    Amplify.API.query(ModelQuery.list(Tag::class.java, Tag.NAME.eq(name)),
                        { findResult ->
                            // see if it exists
                            val items = findResult.data.items as ArrayList
                            if (items.size > 0 && items.stream().findFirst()
                                    .get().status == TagStatus.ALLOWED
                            ) {
                                postSingleTag(items.stream().findFirst().get())
                            } else {
                                // create it if it doesnt
                                tagToCreate.postValue(name)
                            }
                        },
                        {
                            Log.e(TAG, "Failed to find tag $name", it)
                        })
                }
            }
        } else _tags.postValue(null)
    }

    private fun postSingleTag(tag: Tag) {
        if (_tags.value.isNullOrEmpty()) _tags.postValue(listOf(tag))
        else {
            val newList = _tags.value!!.toMutableList()
            newList.add(tag)
            _tags.postValue(newList)
        }
    }

}