package com.example.palfinder.views.groups

import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.amplifyframework.datastore.generated.model.GroupMembers
import com.example.palfinder.R
import com.example.palfinder.backend.services.GroupAdmin

class GroupMembersRecyclerView(
    private val groupMembers: MutableList<GroupMembers>,
    private val group: GroupAdmin.GroupModel,
    private val listener: OnViewProfileListener?
) :
    RecyclerView.Adapter<GroupMembersRecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    override fun getItemCount() = groupMembers?.size ?: 0

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imageView: ImageView = view.findViewById(R.id.iv_image)
        val nameView: TextView = view.findViewById(R.id.tv_name)
        val descriptionView: TextView = view.findViewById(R.id.tv_description)
        val btnJoin: Button = view.findViewById(R.id.btnJoin)
        val textJoined: String = view.context.getString(R.string.group_profile_joined)
    }
}