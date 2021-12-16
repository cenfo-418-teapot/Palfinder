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
//            val finalUserDescription = finalUser.description
//            val finalImage = finalUser.photo
//            holder.descriptionView.text = finalUserDescription
//            if (finalImage != null) {
//                holder.imageView.setImageBitmap(finalUser.photo)
//            }
            holder.userFullNameView.text = finalUserFullName
            holder.usernameView.text = finalUsername
            if(listener !== null) {
                holder.imageView.setOnClickListener {
//                    listener.onClickViewProfile(item)
                }
                holder.btnJoin.setOnClickListener {
                    Log.i(TAG, "Removed user ${finalUser.username} group: ${group.name}")
                    holder.btnJoin.isEnabled = false
                    listener.onUnJoinGroup(item)
                }
            }
        }

    }

    override fun getItemCount() = groupMembers?.size ?: 0

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imageView: ImageView = view.findViewById(R.id.ivUserPicture)
        val userFullNameView: TextView = view.findViewById(R.id.tvFullName)
        val usernameView: TextView = view.findViewById(R.id.tvUsername)
//        val descriptionView: TextView = view.findViewById(R.id.tv_description)
        val btnJoin: Button = view.findViewById(R.id.btnJoin)
    }

    companion object {
        private const val TAG = "GroupMembersRecyclerViewAdapter"
    }
}