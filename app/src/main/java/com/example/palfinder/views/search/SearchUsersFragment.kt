package com.example.palfinder.views.search

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.amplifyframework.api.graphql.model.ModelQuery
import com.amplifyframework.core.Amplify
import com.amplifyframework.datastore.generated.model.Tag
import com.amplifyframework.datastore.generated.model.User
import com.example.palfinder.R
import kotlinx.android.synthetic.main.fragment_search.view.*

class SearchUsersFragment : Fragment() {
    private val userList = MutableLiveData<List<User>>()
    private var filteredUserList = listOf<User>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_search, container, false)
        initRecyclerView(view.rvSearch)
        SearchData.searchTerm.observe(viewLifecycleOwner, {
            if (!userList.value.isNullOrEmpty()) {
                val results = filterUsers(it)
                view.rvSearch.adapter = UserItemAdapter(results)
            }
        })
        SearchData.tags.observe(viewLifecycleOwner, { updateListByTags(it, view.rvSearch) })
        return view
    }

    private fun updateListByTags(tags: List<Tag>?, rv: RecyclerView) {
        if (!userList.value.isNullOrEmpty()) {
            filterUsersByTags(tags)
            val results = filterUsers(SearchData.searchTerm.value)
            rv.adapter = UserItemAdapter(results)
        }
    }

    private fun filterUsers(searchTerm: String?): List<User> {
        return if (searchTerm.isNullOrBlank()) filteredUserList else filteredUserList
            .filter { user ->
                user.username.contains(searchTerm) || user.lastName.contains(searchTerm) || user.name.contains(
                    searchTerm
                )
            }
    }

    private fun filterUsersByTags(tags: List<Tag>?) {
        filteredUserList =
            if (tags.isNullOrEmpty()) userList.value ?: listOf() else userList.value!!
                .filter { user ->
                    var res = true
                    tags.forEach { tag ->
                        if (user.tags.find { it.tag.id == tag.id } == null) res = false
                    }
                    res
                }
    }

    private fun initRecyclerView(rv: RecyclerView) {
        rv.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        rv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        searchUsers()
        userList.observe(viewLifecycleOwner, { users ->
            if (users.isNotEmpty()) requireActivity().findViewById<View>(R.id.tilSearch).isEnabled =
                true
            updateListByTags(SearchData.tags.value, rv)
        })
    }

    private fun searchUsers() {
        Amplify.API.query(
            ModelQuery.list(User::class.java),
            {
                userList.postValue(it.data.items as List<User>)
            },
            { Log.e(TAG, "Error getting initial user list", it) }
        )
    }

    companion object {
        private const val TAG = "SearchUserFragment"
    }
}