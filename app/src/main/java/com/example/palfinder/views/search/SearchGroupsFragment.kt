package com.example.palfinder.views.search

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.amplifyframework.datastore.generated.model.Tag
import com.example.palfinder.R
import com.example.palfinder.backend.services.GroupAdmin
import com.example.palfinder.backend.services.GroupService
import com.example.palfinder.backend.services.InitialSetupData
import com.example.palfinder.views.groups.GroupProfileActivity
import com.example.palfinder.views.groups.GroupSharedViewModel
import com.example.palfinder.views.user.account.InitialGroupSelectionFragment
import kotlinx.android.synthetic.main.fragment_search.view.*

class SearchGroupsFragment : Fragment() {

    private val allItemsList = MutableLiveData<List<GroupAdmin.GroupModel>>()
    private var filteredItemsList = listOf<GroupAdmin.GroupModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =inflater.inflate(R.layout.fragment_search, container, false)
        initRecyclerView(view.rvSearch)
        SearchData.searchTerm.observe(viewLifecycleOwner, {
            if (!GroupAdmin.groups().value.isNullOrEmpty()) {
                val results = filterGroups(it)
                view.rvSearch.adapter = GroupsAdapter(results)
            }
        })
        SearchData.tags.observe(viewLifecycleOwner, { updateListByTags(it, view.rvSearch) })
        return view
    }

    private fun filterGroups(searchTerm: String?): List<GroupAdmin.GroupModel> {
        return if (searchTerm.isNullOrBlank()) filteredItemsList else filteredItemsList
            .filter { item ->
                item.data.name.contains(searchTerm) || item.data.description.contains(searchTerm)
            }.toMutableList()
    }

    private fun initRecyclerView(rv: RecyclerView) {
        rv.layoutManager = GridLayoutManager(context, 2)
        searchItems()
        allItemsList.observe(viewLifecycleOwner, { users ->
            if (users.isNotEmpty()) requireActivity().findViewById<View>(R.id.tilSearch).isEnabled =
                true
            updateListByTags(SearchData.tags.value, rv)
        })
    }

    private fun searchItems() {
        GroupService.updateGroups()
        GroupAdmin.groups()
            .observe(viewLifecycleOwner, { groups ->
                Log.d(TAG, "Note observer received ${groups.size} groups")
                // let's create a RecyclerViewAdapter that manages the individual cells
                allItemsList.postValue(groups.toList())
            })
    }

    private fun filterItemsByTags(tags: List<Tag>?) {
        filteredItemsList =
            if (tags.isNullOrEmpty()) allItemsList.value ?: mutableListOf() else allItemsList.value!!
                .filter { item ->
                    var res = true
                    tags.forEach { tag ->
                        if (item.tags?.find { it.tag.id == tag.id } == null) res = false
                    }
                    res
                }
    }


    private fun updateListByTags(tags: List<Tag>?, rv: RecyclerView) {
        if (!allItemsList.value.isNullOrEmpty()) {
            filterItemsByTags(tags)
            val results = filterGroups(SearchData.searchTerm.value)
            rv.adapter = GroupsAdapter(results)
        }
    }

    inner class GroupsAdapter(
        private val values: List<GroupAdmin.GroupModel>?
    ) :
        RecyclerView.Adapter<GroupsAdapter.ViewHolder>() {

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
            holder.imageView.setOnClickListener {
                val model = ViewModelProvider(requireActivity()).get(GroupSharedViewModel::class.java)
                if (item != null) {
                    model.sendMessage(item)
                    it.context.startActivity(Intent(it.context, GroupProfileActivity::class.java))
                } else {
                    Toast.makeText(
                        activity,
                        "Cannot access this data to group profile: " + item,
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
            holder.btnJoin.visibility = View.GONE
        }

        override fun getItemCount() = values?.size ?: 0

        inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            val imageView: ImageView = view.findViewById(R.id.iv_image)
            val nameView: TextView = view.findViewById(R.id.tv_name)
            val descriptionView: TextView = view.findViewById(R.id.tv_description)
            val btnJoin: Button = view.findViewById(R.id.btnJoin)
            lateinit var groupId: String
        }
    }
    companion object {
        private const val TAG = "SearchGroupsFragment"
    }
}
