package com.example.palfinder.views.search

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.amplifyframework.datastore.generated.model.User
import com.example.palfinder.R

class UserItemAdapter(private val list: List<User>) : RecyclerView.Adapter<UserItemAdapter.ViewHolder>() {
    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val tvUsername: TextView = view.findViewById(R.id.tvUsername)
        val tvFullName: TextView = view.findViewById(R.id.tvFullName)
        val ivUserPicture: ImageView = view.findViewById(R.id.ivUserPicture)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.user_item_row, parent, false)
        return ViewHolder(view)
    }
    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val user = list[position]
        val username = "@${user.username}"
        val fullName = "${user.name} ${user.lastName}"
        viewHolder.tvUsername.text = username
        viewHolder.tvFullName.text = fullName
    }

    override fun getItemCount() = list.size}
