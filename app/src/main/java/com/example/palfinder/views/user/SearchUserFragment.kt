package com.example.palfinder.views.user

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import com.amplifyframework.datastore.generated.model.User
import com.amplifyframework.datastore.generated.model.UserStatus
import com.example.palfinder.R
import com.example.palfinder.views.search.UserItemAdapter
import kotlinx.android.synthetic.main.fragment_search_user.*
import kotlinx.android.synthetic.main.fragment_search_user.view.*

class SearchUserFragment : Fragment() {
    private val userList: MutableList<User> = mutableListOf()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_search_user, container, false)
        view.etSearchUser.requestFocus()
        initUserList()
        view.rvUserItems.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        view.rvUserItems.adapter = UserItemAdapter(userList)
        view.etSearchUser.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun afterTextChanged(p0: Editable?) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (!p0.isNullOrBlank()) searchUser(p0.toString())
            }
        })
        return view
    }

    private fun searchUser(text: String) {
//        Call amplify with a search query, for testing here is a mutable list
        rvUserItems.adapter = UserItemAdapter(userList.filter { user ->
            user.name!!.lowercase().contains(text) ||
                    user.lastName!!.lowercase().contains(text) ||
                    user.username!!.lowercase().contains(text)
        })
    }

    private fun initUserList() {
        for (i in 1..10) {
            val user =
                User.builder().email("demo$i@mail.com").username("snsotomdemo$i").name("Sebastian")
                    .lastName("Soto #$i").status(UserStatus.COMPLETE).build()
            userList.add(user)
        }
    }
}