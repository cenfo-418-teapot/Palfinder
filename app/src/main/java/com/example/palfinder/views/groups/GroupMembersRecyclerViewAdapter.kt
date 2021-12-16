package com.example.palfinder.views.groups

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.amplifyframework.datastore.generated.model.GroupMembers
import com.amplifyframework.datastore.generated.model.GroupRoles
import com.example.palfinder.R
import com.example.palfinder.backend.services.GroupAdmin

class GroupMembersRecyclerViewAdapter(
    private val groupMembers: MutableList<GroupMembers>?,
    private val group: GroupAdmin.GroupModel,
    private val listener: OnShowProfileListener?
    ) :
    RecyclerView.Adapter<GroupMembersRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.members_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = groupMembers?.get(position)

        val finalUser = item?.user
        if(finalUser != null) {
            val finalUserFullName = finalUser.name + " " + finalUser.lastName
            val finalUsername = finalUser.username
            val finalUserDescription = finalUser.description
            val finalUserRole = item.role.toString()
//            val finalImage = finalUser.photo
//            if (finalImage != null) {
//                holder.imageView.setImageBitmap(finalUser.photo)
//            }
            holder.userFullNameView.text = finalUserFullName
            holder.usernameView.text = finalUsername
            holder.descriptionView.text = finalUserDescription
            holder.btnRole.text = finalUserRole
            if(item.role == GroupRoles.OWNER) holder.btnRemove.isEnabled = false
            if(listener !== null) {
                holder.imageView.setOnClickListener {
//                    listener.onClickViewProfile(item)
                }
                holder.btnRemove.setOnClickListener {
                    Log.i(TAG, "Removed user ${finalUser.username} group: ${group.name}")
                    holder.btnRemove.isEnabled = false
                    listener.onUnJoinGroup(item)
                }
            }
        }

    }

    override fun getItemCount() = groupMembers?.size ?: 0

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imageView: ImageView = view.findViewById(R.id.iv_user_picture)
        val userFullNameView: TextView = view.findViewById(R.id.tv_full_name)
        val usernameView: TextView = view.findViewById(R.id.tv_username)
        val descriptionView: TextView = view.findViewById(R.id.tv_description)
        val btnRole: Button = view.findViewById(R.id.btn_role)
        val btnRemove: Button = view.findViewById(R.id.btn_remove)
    }

    companion object {
        private const val TAG = "GroupMembersRecyclerViewAdapter"
    }
}