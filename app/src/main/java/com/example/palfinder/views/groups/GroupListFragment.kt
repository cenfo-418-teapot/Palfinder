package com.example.palfinder.views.groups

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.amplifyframework.api.graphql.model.ModelMutation
import com.amplifyframework.core.Amplify
import com.amplifyframework.datastore.generated.model.*
import com.example.palfinder.R
import com.example.palfinder.backend.services.GroupAdmin
import com.example.palfinder.backend.services.GroupService
import com.example.palfinder.backend.services.UserData
import com.example.palfinder.backend.services.UserService
import kotlinx.android.synthetic.main.fragment_event_list.*
import kotlinx.android.synthetic.main.fragment_group_list.*
import kotlinx.android.synthetic.main.fragment_group_list.view.*

/**
 * A simple [Fragment] subclass.
 * Use the [GroupListFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class GroupListFragment : Fragment(), OnViewProfileListener {
    private val progressLiveData = MutableLiveData<String>()

    //    private lateinit var progressBar: ProgressBar
    private val currentUser = MutableLiveData<User?>()
//    private var userTags: List<TagUser>? = null
//    private var userGroups: List<GroupMembers>? = null
    private val groupToAddLiveData = MutableLiveData<GroupAdmin.GroupModel>()
    private val groupToRemoveLiveData = MutableLiveData<GroupMembers>()
    private var discovering = true
    private var recyclerViewUp = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_group_list, container, false)
        view.nav_discover_groups.setOnClickListener { setFocus(view, 1) }
        view.nav_my_groups.setOnClickListener { setFocus(view, 2) }
        view.nav_create_group.setOnClickListener { setFocus(view, 3) }
        view.refresh_groups.setOnClickListener { loadGroups(true) }

        if(currentUser.value == null)
            retrieveUser()

        if(!recyclerViewUp)
            observeCurrentUser()

//        progressBar = requireActivity().findViewById(R.id.progressBar4)
//        progressBar.progress = 0
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadGroups(false)
    }

    private fun loadGroups(notify: Boolean) {
        if(notify) {
            GroupAdmin.notifyObserver()
        } else {
            GroupService.updateGroups()
        }
    }

    private fun retrieveUser() {
        UserService.getUserByUsername(
            Amplify.Auth.currentUser.username,
            {
                val items = it.data.items as ArrayList
                when {
                    items.size > 0 -> {
                        val user = items.stream().findFirst().get()
                        UserData.setCurrentUser(user)
                        currentUser.postValue(user)
                    }
                }
            },
            { Log.e(TAG, "Failed to get user by id", it) }
        )

        groupToAddLiveData.observe(viewLifecycleOwner, { groupToAdd ->
            if (groupToAdd != null && currentUser.value != null) {
                addGroupToUser(currentUser.value!!.groups, groupToAdd)
            }
        })
        groupToRemoveLiveData.observe(viewLifecycleOwner, { memberToRemove ->
            if (memberToRemove != null && currentUser.value != null) {
                removeGroupFromUser(memberToRemove)
            }
        })
    }

    private fun observeCurrentUser(){
        currentUser.observe(viewLifecycleOwner, {
            if (it != null) {
//                val tmpTags = it.tags
//                if (!tmpTags.isNullOrEmpty()) {
//                    userTags = tmpTags
//                }
//                val tmpGroups = it.groups
//                if (!tmpGroups.isNullOrEmpty()) {
//                    userGroups = tmpGroups
//                }
                setupRecyclerView(group_list)
            }
        })
    }

    private fun setFocus(view: View, navOption: Int) {
        when (navOption) {
            1 -> {
                underline_discover_groups.visibility = View.VISIBLE
                underline_my_groups.visibility = View.INVISIBLE
                underline_create_group.visibility = View.INVISIBLE
                tv_suggested_subtitle.text = getString(R.string.group_list_my_suggested_subtitle)
                discovering = true
                GroupAdmin.notifyObserver()
            }
            2 -> {
                underline_discover_groups.visibility = View.INVISIBLE
                underline_my_groups.visibility = View.VISIBLE
                underline_create_group.visibility = View.INVISIBLE
                tv_suggested_subtitle.text = getString(R.string.group_list_my_groups_subtitle)
                discovering = false
                GroupAdmin.notifyObserver()
            }
            3 -> {
                underline_discover_groups.visibility = View.INVISIBLE
                underline_my_groups.visibility = View.INVISIBLE
                underline_create_group.visibility = View.VISIBLE
                Navigation.findNavController(view)
                    .navigate(R.id.action_groupListFragment_to_groupEditFragment)
            }
        }
    }

    private fun setupRecyclerView(recyclerView: RecyclerView) {
        // add a touch gesture handler to manager the swipe to delete gesture
        val itemTouchHelper = ItemTouchHelper(GroupSwipeCallback(activity as AppCompatActivity))
        itemTouchHelper.attachToRecyclerView(recyclerView)
        // update individual cell when the Group data are modified
        GroupAdmin.groups()
            .observe(viewLifecycleOwner, Observer<MutableList<GroupAdmin.GroupModel>> { groups ->
                Log.d(TAG, "Note observer received ${groups.size} groups")

                val groupsIds: MutableList<String> = mutableListOf()
                currentUser.value!!.groups.forEach{ userGroup ->
                    groupsIds.add(userGroup.group.id)
                }

                // filtering depending of list tabs
                val filteredGroups = groups.filter { group ->
                    (group.status == Status.PUBLIC && discovering && !groupsIds.contains(group.id)) ||
                    (!discovering && groupsIds.contains(group.id))
                }

                val userTagsNames: MutableList<String> = mutableListOf()
                currentUser.value!!.tags.forEach { userTag ->
                    userTagsNames.add(userTag.tag.name)
                }
                // sorting depending of tags
                val sortedGroups = filteredGroups.sortedBy {
                    compareValues(
                        userTagsNames.joinToString(separator = ":"),
                        it.getTagsNames().joinToString(separator = ":")
                    )
                }

                // let's create a RecyclerViewAdapter that manages the individual cells
                recyclerView.adapter = GroupsRecyclerViewAdapter(
                    sortedGroups.toMutableList(),
                    this,
                    currentUser.value!!.groups)
                if (groups.size > 0) tv_no_groups.visibility = View.GONE
                else tv_no_groups.visibility = View.VISIBLE
            })
    }

    override fun onClickViewProfile(data: GroupAdmin.GroupModel?) {
        this.view?.let {
            val model = ViewModelProvider(requireActivity()).get(GroupSharedViewModel::class.java)
            if (data != null) {
                model.sendMessage(data)
                Navigation.findNavController(it)
                    .navigate(R.id.action_groupListFragment_to_groupProfile)
            } else {
                Toast.makeText(
                    activity,
                    "Cannot access this data to group profile: " + data,
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    override fun onJoinGroup(data: GroupAdmin.GroupModel?) {
        groupToAddLiveData.postValue(data)
    }

    override fun onUnJoinGroup(data: GroupMembers?) {
        groupToRemoveLiveData.postValue(data)
    }

    private fun addGroupToUser(currentGroups: List<GroupMembers>?, group: GroupAdmin.GroupModel) {
        val userIsMember = currentGroups?.find { member -> member.group.id == group.id }
        if (userIsMember != null) {
            progressLiveData.postValue("User was already a member of: ${group.name}")
        } else {
            val member = GroupMembers.builder().role(GroupRoles.PARTICIPANT).user(currentUser.value).group(group.data).build()
            if (currentUser.value != null){
                Amplify.API.mutate(
                    ModelMutation.create(member),
                    {
                        currentUser.value!!.groups.add(it.data)
                        progressLiveData.postValue("${currentUser.value!!.username} added to group ${group.name}")
                        loadGroups(true)
                    },
                    { Log.e(TAG, "${currentUser.value!!.username} was not added as a member to ${group.name}") }
                )
            }
        }
    }

    private fun removeGroupFromUser(member: GroupMembers) {
        if (currentUser.value != null){
            Amplify.API.mutate(
                ModelMutation.delete(member),
                {
                    currentUser.value!!.groups.remove(it.data)
                    progressLiveData.postValue("${currentUser.value!!.username} removed from group ${member.group.name}")
                    loadGroups(true)
                },
                { Log.e(TAG, "${currentUser.value!!.username} was not added as a member to ${member.group.name}") }
            )
        }
    }

    companion object {
        private const val TAG = "GroupListFragment"
    }
}
