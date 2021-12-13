package com.example.palfinder.views.groups

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.palfinder.R
import com.example.palfinder.backend.services.GroupAdmin
import android.util.Log
import android.view.View.GONE
import android.widget.Button
import com.amazonaws.mobile.auth.core.internal.util.ThreadUtils.runOnUiThread
import com.amplifyframework.datastore.generated.model.GroupMembers
import com.google.android.material.card.MaterialCardView

class GroupsRecyclerViewAdapter(
    private val values: MutableList<GroupAdmin.GroupModel>?,
    private val listener: OnViewProfileListener?,
    private val userGroups: List<GroupMembers>,
    private val showAll: Boolean
    ) :
    RecyclerView.Adapter<GroupsRecyclerViewAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.group_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values?.get(position)

        val finalName = item?.name
        val finalDescription = item?.description
        val finalImage = item?.image
        holder.nameView.text = finalName
        holder.descriptionView.text = finalDescription
        if (finalImage != null) {
            holder.imageView.setImageBitmap(item.image)
        }
        if(listener !== null) {
            holder.imageView.setOnClickListener {
                listener.onClickViewProfile(item)
            }
            var isMember = false
            var tempGroupMember: GroupMembers = GroupMembers.justId("null")
            if(!userGroups.isNullOrEmpty()){
                userGroups.forEach{
                    if(it.group != null && it.group.id == item?.id) {
                        holder.btnJoin.text = holder.textJoined
                        //holder.btnJoin.isEnabled = false
                        isMember = true
                        tempGroupMember = it
                    }
                }
            }
            if((!isMember && !showAll) || (isMember && showAll)) {
                // TODO : CUANDO UN GRUPO NO SE DEBE MOSTRAR
//                holder.card.visibility = GONE
//
//                runOnUiThread {
//                    val actualPosition = holder.adapterPosition
//                    values?.removeAt(actualPosition)
//                    notifyItemRemoved(actualPosition)
//                    notifyItemRangeChanged(actualPosition, values!!.size)
//                }
            } else {
                if(isMember) {
                    holder.btnJoin.setOnClickListener {
                        Log.i(TAG, "Unjoining group: $finalName")
                        listener.onUnJoinGroup(tempGroupMember)
                        holder.btnJoin.isEnabled = false
                    }
                } else {
                    holder.btnJoin.setOnClickListener {
                        Log.i(TAG, "Joining group: $finalName")
                        listener.onJoinGroup(item)
                        holder.btnJoin.isEnabled = false
                    }
                }
            }
        }
    }

    override fun getItemCount() = values?.size ?: 0

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imageView: ImageView = view.findViewById(R.id.iv_image)
        val nameView: TextView = view.findViewById(R.id.tv_name)
        val descriptionView: TextView = view.findViewById(R.id.tv_description)
        val btnJoin: Button = view.findViewById(R.id.btnJoin)
        val card: MaterialCardView = view.findViewById(R.id.card_info)
        val textJoined: String = view.context.getString(R.string.group_profile_joined)
    }
    companion object {
        private const val TAG = "GroupsRecyclerViewAdapter"
    }
}