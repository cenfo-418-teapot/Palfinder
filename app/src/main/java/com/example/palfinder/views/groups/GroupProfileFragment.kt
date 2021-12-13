package com.example.palfinder.views.groups

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.amplifyframework.api.graphql.model.ModelMutation
import com.amplifyframework.core.Amplify
import com.amplifyframework.datastore.generated.model.TagGroup
import com.amplifyframework.datastore.generated.model.User
import com.example.palfinder.R
import com.example.palfinder.backend.services.GroupAdmin
import com.example.palfinder.backend.services.UserData
import com.example.palfinder.backend.services.UserService
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import kotlinx.android.synthetic.main.fragment_group_edit.view.*
import kotlinx.android.synthetic.main.fragment_group_list.*
import kotlinx.android.synthetic.main.fragment_group_list.view.*
import kotlinx.android.synthetic.main.fragment_group_profile.*
import kotlinx.android.synthetic.main.fragment_group_profile.tvTitle
import kotlinx.android.synthetic.main.fragment_group_profile.view.*
import kotlinx.android.synthetic.main.group_item.*
import java.util.*

/**
 * A simple [Fragment] subclass.
 * Use the [GroupProfile.newInstance] factory method to
 * create an instance of this fragment.
 */
class GroupProfile : Fragment() {
    lateinit var group: GroupAdmin.GroupModel
    private val currentUser = MutableLiveData<User?>()
    private val messageToShow = MutableLiveData<String>()
    lateinit var model: GroupSharedViewModel
    private var canShare = false
    private var canMessaging = false
    private var dataRetrieved = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_group_profile, container, false)
        view.tv_grup_view_all_members?.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_groupProfile_to_groupUsersFragment)
        }
        view.nav_add_user.setOnClickListener{ setFocus(view,1) }
        view.nav_create_event.setOnClickListener{ setFocus(view, 2) }
        view.nav_share.setOnClickListener{ setFocus(view, 3) }
        retrieveUser()
        currentUser.observe(viewLifecycleOwner, {
            observeGroup(view)
        })
        observeMessages()
        return view
    }

    private fun retrieveUser() {
        if(currentUser.value == null) {
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
        }
    }

    private fun setFocus(view: View, navOption: Int){
        when(navOption) {
            1 -> {
                underline_add_user.visibility = View.VISIBLE
                underline_create_event.visibility = View.INVISIBLE
                underline_share.visibility = View.INVISIBLE
            }
            2 -> {
                underline_add_user.visibility = View.INVISIBLE
                underline_create_event.visibility = View.VISIBLE
                underline_share.visibility = View.INVISIBLE
            }
            3 -> {
                underline_add_user.visibility = View.INVISIBLE
                underline_create_event.visibility = View.INVISIBLE
                underline_share.visibility = View.VISIBLE
            }
        }
    }


    private fun observeGroup(view: View) {
        if(!dataRetrieved){
            model = ViewModelProvider(requireActivity()).get(GroupSharedViewModel::class.java)
            model.message.observe(viewLifecycleOwner, {
                this.group = it
                Log.d(TAG, "Group RECEIVED! " + group.name)
                setGroupData()
                observeShareGroup()
                view.edit_group.setOnClickListener{
                    model.sendMessage(group)
                    Navigation.findNavController(view)
                        .navigate(R.id.action_groupProfile_to_groupProfileEditFragment)
                }
            })
        }
    }

    private fun setGroupData() {
        tv_privacy_subtitle.text = group.status.toString()
            .replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }
        tvTitle.text = group.name
        tv_group_description.text = group.description
        iv_image_profile.setImageBitmap(group.image)
        val tempTags: MutableList<String> = mutableListOf()
        if(!group.tags.isNullOrEmpty()) {
            tv_group_tag_list.visibility = View.GONE
            cgGroupTags.visibility = View.VISIBLE

            group.tags!!.forEach {
                tempTags.add(it.tag.name)
            }
            group.tags!!.forEach {
                // in text
//                val tempText: String = tv_group_tag_list.text.toString()
//                tv_group_tag_list.text = tempText + " " + it.tag.name
                // in chips
                val chip = addProfileTagChips(it.tag.name, cgGroupTags)
                chip?.setOnCloseIconClickListener { chp ->
                    cgGroupTags.removeView(chp)
                    deleteGroupTag(it) { response ->
                        messageToShow.postValue(response)
                    }
                }
            }
        } else {
            tv_group_tag_list.visibility = View.VISIBLE
            cgGroupTags.visibility = View.GONE
            tempTags.add("No tags found.")
            var cont = 0
            tv_group_tag_list.text = ""
            tempTags.forEach {
                cont++
                var tempText: String = tv_group_tag_list.text.toString()
                if(cont > 1 && cont <= tempTags.size) tempText += ", "
                tv_group_tag_list.text = tempText  + " " + it
            }
        }
    }


    private fun observeMessages() {
        if (!canMessaging) {
            canMessaging = true
            messageToShow.observe(viewLifecycleOwner, {
                showMessage(it)
            })
        }
    }

    private fun showMessage(message: String) {
        Toast.makeText(
            activity,
            message,
            Toast.LENGTH_SHORT
        ).show()
    }

    private fun deleteGroupTag(groupTag: TagGroup, textResponse: (String) -> Unit){
        if (currentUser.value != null){
            Amplify.API.mutate(
                ModelMutation.delete(groupTag),
                {
                    GroupAdditionalSetUp.removeTag(groupTag.tag.name)
                    textResponse("Tag ${groupTag.tag.name} removed from group " +
                            "${groupTag.group.name} by ${currentUser.value!!.username} ")
                },
                {
//                    Log.e(TAG, "${currentUser.value!!.username} was a tag from to ${groupTag.group.name}")
                    textResponse("Cannot remove tag ${groupTag.tag.name}")
                    addProfileTagChips(groupTag.tag.name, cgGroupTags)
                }
            )
        }
    }

    private fun addProfileTagChips(name: String, chipGroup: ChipGroup): Chip? {
        val exists =
            chipGroup.checkedChipIds.any { id -> chipGroup.findViewById<Chip>(id).text == name }
        if (!exists) {
            val chip = layoutInflater.inflate(R.layout.group_tag, chipGroup, false) as Chip
            chip.text = name
            chip.setOnCloseIconClickListener {
                GroupAdditionalSetUp.setTagsList(
                    GroupAdditionalSetUp.tagsList.value!!
                        .filter { tag -> tag != name }
                        .toList()
                )
            }
            chipGroup.addView(chip)
            Log.i(TAG, "Added chip $name")
            return chip
        }
        return null
    }

    private fun observeShareGroup() {
        if(!canShare) {
            nav_share.setOnClickListener {
                val intent = Intent()
                intent.action = Intent.ACTION_SEND
                intent.putExtra(
                    Intent.EXTRA_TEXT,
                    "Hey! Check out this great group in Palfinder app: ${group.name}"
                )
                intent.type = "text/plain"
                startActivity(Intent.createChooser(intent, "Share To:"))
            }
            canShare = true
        }
    }

    companion object {
        private const val TAG = "GroupProfileFragment"
    }
}