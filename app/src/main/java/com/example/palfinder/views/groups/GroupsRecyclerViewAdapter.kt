package com.example.palfinder.views.groups

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.palfinder.R
import com.example.palfinder.backend.services.GroupAdmin
import com.example.palfinder.backend.services.GroupService
import kotlinx.android.synthetic.main.group_item.view.*
import java.util.*

class GroupsRecyclerViewAdapter(
    private val values: MutableList<GroupAdmin.GroupModel>?) :
    RecyclerView.Adapter<GroupsRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.group_item, parent, false)

        view.iv_image?.setOnClickListener {
            Navigation.findNavController(view)
                .navigate(R.id.action_groupListFragment_to_groupProfile)
        }
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
    }

    override fun getItemCount() = values?.size ?: 0

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imageView: ImageView = view.findViewById(R.id.iv_image)
        val nameView: TextView = view.findViewById(R.id.tv_name)
        val descriptionView: TextView = view.findViewById(R.id.tv_description)
        lateinit var groupId: String
    }
}