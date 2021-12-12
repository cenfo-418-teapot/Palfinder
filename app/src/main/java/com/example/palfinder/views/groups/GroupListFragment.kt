package com.example.palfinder.views.groups

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.ItemTouchHelper
import com.amplifyframework.api.graphql.model.ModelMutation
import com.amplifyframework.api.graphql.model.ModelQuery
import com.amplifyframework.core.Amplify
import com.amplifyframework.datastore.generated.model.GroupMembers
import com.amplifyframework.datastore.generated.model.TagUser
import com.amplifyframework.datastore.generated.model.User
import com.example.palfinder.R
import com.example.palfinder.backend.services.GroupAdmin
import com.example.palfinder.backend.services.GroupService
import com.example.palfinder.backend.services.UserData
import com.example.palfinder.backend.services.UserService
import com.example.palfinder.views.HomeActivity
import kotlinx.android.synthetic.main.fragment_event_list.*
import kotlinx.android.synthetic.main.fragment_group_list.*
import kotlinx.android.synthetic.main.fragment_group_list.view.*

/**
 * A simple [Fragment] subclass.
 * Use the [GroupListFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class GroupListFragment : Fragment(), OnViewProfileListener{
    private val progressLiveData = MutableLiveData<String>()
//    private lateinit var progressBar: ProgressBar
    private lateinit var currentUser: User
    private var userTags: List<TagUser>? = null
    private var userGroups: List<GroupMembers>? = null
    private val groupToAddLiveData = MutableLiveData<GroupAdmin.GroupModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_group_list, container, false)
        view.nav_discover_groups.setOnClickListener{ setFocus(view,1) }
        view.nav_my_groups.setOnClickListener{ setFocus(view, 2) }
        view.nav_create_group.setOnClickListener{ setFocus(view, 3) }
        fetchUser()
//        progressBar = requireActivity().findViewById(R.id.progressBar4)
//        progressBar.progress = 0
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView(group_list)
        GroupService.updateGroups()
    }

    private fun fetchUser() {
        UserService.getUserByUsername(
            Amplify.Auth.currentUser.username,
            {
                val items = it.data.items as ArrayList
                when {
                    items.size > 0 -> {
                        val user = items.stream().findFirst().get()
                        UserData.setCurrentUser(user)
                        currentUser = user
                        fetchData()
                        observeGroupsToAdd()
                    }
                }
            },
            { Log.e(TAG, "Failed to get user by id", it) }
        )
    }

    private fun setFocus(view: View, navOption: Int){
        when(navOption) {
            1 -> {
                underline_discover_groups.visibility = View.VISIBLE
                underline_my_groups.visibility = View.INVISIBLE
                underline_create_group.visibility = View.INVISIBLE
                tv_suggested_subtitle.text = getString(R.string.group_list_my_suggested_subtitle)
            }
            2 -> {
                underline_discover_groups.visibility = View.INVISIBLE
                underline_my_groups.visibility = View.VISIBLE
                underline_create_group.visibility = View.INVISIBLE
                tv_suggested_subtitle.text = getString(R.string.group_list_my_groups_subtitle)
            }
            3 -> {
                underline_discover_groups.visibility = View.INVISIBLE
                underline_my_groups.visibility = View.INVISIBLE
                underline_create_group.visibility = View.VISIBLE
                Navigation.findNavController(view).navigate(R.id.action_groupListFragment_to_groupEditFragment)
            }
        }
    }

    private fun setupRecyclerView(recyclerView: RecyclerView) {
        // add a touch gesture handler to manager the swipe to delete gesture
        val itemTouchHelper = ItemTouchHelper(GroupSwipeCallback(activity as AppCompatActivity))
        itemTouchHelper.attachToRecyclerView(recyclerView)
        // update individual cell when the Group data are modified
        GroupAdmin.groups().observe(viewLifecycleOwner, Observer<MutableList<GroupAdmin.GroupModel>> { groups ->
            Log.d(TAG, "Note observer received ${groups.size} groups")

            // let's create a RecyclerViewAdapter that manages the individual cells
            recyclerView.adapter = GroupsRecyclerViewAdapter(groups, this)
            if(groups.size > 0) tv_no_groups.visibility = View.GONE
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
                    Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onJoinGroup(data: GroupAdmin.GroupModel?) {
        GroupAdditionalSetUp.setGroup(data)
        groupToAddLiveData.postValue(GroupAdditionalSetUp.tmpGroup.value)
    }

    private fun fetchData() {
        Amplify.API.query(ModelQuery.get(User::class.java, currentUser.id),
            { response ->
                UserData.setCurrentUser(response.data)
                currentUser = response.data
                val tmpTags = currentUser.tags
                if(tmpTags !== null && tmpTags.size > 0) {
                    userTags = tmpTags
                }
                val tmpGroups = currentUser.groups
                if(tmpTags !== null && tmpTags.size > 0) {
                    userGroups = tmpGroups
                }
            },
            { Log.e(TAG, "Couldn't fetch the user's tags", it) }
        )
    }

    private fun observeGroupsToAdd() {
        groupToAddLiveData.observe(viewLifecycleOwner, { groupToAdd ->
            if (groupToAdd != null) {
                Amplify.API.query(
                    ModelQuery.get(User::class.java, currentUser.id),
                    {
                        UserData.setCurrentUser(it.data)
                        currentUser = it.data
                        addGroupToUser(
                            currentUser.groups,
                            groupToAdd
                        )
                    },
                    { Log.e(TAG, "Couldn't fetch the user's groups", it) })
            } else Log.i(TAG, "No groups were selected before saving changes")
        })
    }

    private fun addGroupToUser(currentGroups: List<GroupMembers>?, group: GroupAdmin.GroupModel) {
        val userIsMember = currentGroups?.find { member -> member.group.id == group.id }
        if (userIsMember != null) {
            progressLiveData.postValue("User was already a member of: ${group.name}")
        } else {
            val member = GroupMembers.builder().user(currentUser).group(group.data).build()
            Amplify.API.mutate(
                ModelMutation.create(member),
                {
                    currentUser.groups.add(it.data)
                    progressLiveData.postValue("${currentUser.username} added to group ${group.name}")
                },
                { Log.e(TAG, "${currentUser.username} was not added as a member to ${group.name}") }
            )
        }
    }

    companion object {
        private const val TAG = "GroupListFragment"
    }
}
