package com.example.palfinder.views.user.account

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import com.amplifyframework.api.graphql.model.ModelMutation
import com.amplifyframework.api.graphql.model.ModelQuery
import com.amplifyframework.core.Amplify
import com.amplifyframework.datastore.generated.model.*
import com.example.palfinder.R
import com.example.palfinder.backend.services.GroupAdmin
import com.example.palfinder.backend.services.InitialSetupData
import com.example.palfinder.backend.services.UserData
import com.example.palfinder.views.HomeActivity
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import kotlinx.android.synthetic.main.fragment_initial_setup_confirmation.view.*

class InitialSetupConfirmationFragment : Fragment() {
    private var currentUser = UserData.currentUser.value!!
    private val groupsToAddLiveData = MutableLiveData<List<GroupAdmin.GroupModel>?>()
    private val _tagsToAdd = ArrayList<Tag>()
    private val tagsToAddLiveData = MutableLiveData<List<Tag>>()
    private val progressLiveData = MutableLiveData<String>()
    private var _finishedAdditions = 0
    private var _totalChanges = 0
    private lateinit var progressBar: ProgressBar

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_initial_setup_confirmation, container, false)
        progressBar = requireActivity().findViewById(R.id.progressBar2)
        progressLiveData.observe(viewLifecycleOwner, { action ->
            Log.i(TAG, "Finished an action | $action")
            _finishedAdditions++
            progressBar.progress = _finishedAdditions
            if (_totalChanges == _finishedAdditions) {
                Amplify.API.query(ModelQuery.get(User::class.java, currentUser.id),
                    { userResponse ->
                        val user = userResponse.data
                        val updatedUser = User.builder()
                            .email(user.email)
                            .username(user.username)
                            .name(InitialSetupData.userInfo.value?.name ?: user.name)
                            .lastName(InitialSetupData.userInfo.value?.lastName ?: user.lastName)
                            .status(UserStatus.COMPLETE)
                            .description(InitialSetupData.userInfo.value?.description ?: user.description)
                            .phoneNumber(user.phoneNumber)
                            .photo(user.photo)
                            .id(user.id)
                            .build()
                        Amplify.API.mutate(
                            ModelMutation.update(updatedUser),
                            { updatedUserResponse ->
                                UserData.setCurrentUser(updatedUserResponse.data)
                                startActivity(Intent(activity, HomeActivity::class.java))
                            },
                            { Log.e(TAG, "Failed to change user status", it) })
                    },
                    { Log.e(TAG, "Failed to get user from database", it) })
            }
        })
        observeTagsAdditionFlow(view)
        observeGroupsAdditionFlow(view)
        observeProfileInfo(view)
        requireActivity().findViewById<Button>(R.id.btnSave).setOnClickListener { _ ->
            val totalTagsToAdd = InitialSetupData.tagsList.value?.size ?: 0
            val totalGroupsToAdd = InitialSetupData.groupsList.value?.size ?: 0
            _totalChanges = totalTagsToAdd + totalGroupsToAdd
            if (_totalChanges == 0) {
                Toast.makeText(context, "You should select some tags or groups before we begin", Toast.LENGTH_LONG).show()
            } else {
                if (progressBar.visibility == View.GONE && _totalChanges > 0) {
                    progressBar.visibility = View.VISIBLE
                    progressBar.max = _totalChanges
                }
                InitialSetupData.tagsList.value?.forEach { createTag(it) }
                groupsToAddLiveData.postValue(InitialSetupData.groupsList.value)
            }
        }
        return view
    }

    private fun observeGroupsAdditionFlow(view: View) {
        observeGroupsList(view)
        observeGroupsToAdd()
    }

    private fun observeTagsAdditionFlow(view: View) {
        observeTagsList(view)
        observeTagsToAdd()
    }

    private fun observeProfileInfo(view: View) {
        InitialSetupData.userInfo.observe(viewLifecycleOwner, { userInfo ->
            view.llProfileInfo.removeAllViews()
            if (userInfo != null) {
                val fullName = "${userInfo.name} ${userInfo.lastName}"
                val tvFullName = TextView(context)
                tvFullName.text = fullName
                tvFullName.layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
                tvFullName.setTextAppearance(R.style.Headline1)
                val tvBio = TextView(context)
                tvBio.text = userInfo.description
                tvBio.layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
                tvBio.setTextAppearance(R.style.Paragraph)
                view.llProfileInfo.addView(tvFullName)
                view.llProfileInfo.addView(tvBio)
            }
            else {
                val tvNoData = TextView(context)
                tvNoData.text = "Profile details are empty"
                tvNoData.layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
                tvNoData.setTextAppearance(R.style.Headline1)
                view.llProfileInfo.addView(tvNoData)
            }
        })
    }
    private fun observeGroupsToAdd() {
        groupsToAddLiveData.observe(viewLifecycleOwner, { groupsToAdd ->
            if (groupsToAdd != null) {
                if (groupsToAdd.isNotEmpty() && groupsToAdd.size == InitialSetupData.groupsList.value?.size ?: 0) {
                    Amplify.API.query(ModelQuery.get(User::class.java, currentUser.id),
                        {
                            UserData.setCurrentUser(it.data)
                            currentUser = it.data
                            groupsToAdd.forEach { group ->
                                addGroupToUser(
                                    currentUser.groups,
                                    group
                                )
                            }
                        },
                        { Log.e(TAG, "Couldn't fetch the user's groups", it) })
                }
            } else Log.i(TAG, "No groups were selected before saving changes")
        })
    }

    private fun addGroupToUser(currentGroups: List<GroupMembers>?, group: GroupAdmin.GroupModel) {
        val userIsMember = currentGroups?.find { member -> member.group.id == group.id }
        if (userIsMember != null) {
            progressLiveData.postValue("User was already a member of: ${group.name}")
        } else {
            val member = GroupMembers.builder().role(GroupRoles.PARTICIPANT).user(currentUser).group(group.data).groupUsersId(group.id).build()
            Amplify.API.mutate(ModelMutation.create(member),
                {
                    currentUser.groups.add(it.data)
                    progressLiveData.postValue("${currentUser.username} added to group ${group.name}")
                },
                { Log.e(TAG, "${currentUser.username} was not added as a member to ${group.name}") }
            )
        }
    }

    private fun observeTagsToAdd() {
        tagsToAddLiveData.observe(viewLifecycleOwner, { tagsToAdd ->
            if (tagsToAdd.size == InitialSetupData.tagsList.value!!.size) {
                Amplify.API.query(ModelQuery.get(User::class.java, currentUser.id),
                    { response ->
                        UserData.setCurrentUser(response.data)
                        currentUser = response.data
                        tagsToAdd.forEach { tag ->
                            addTagToUser(currentUser.tags, tag)
                        }
                    },
                    { Log.e(TAG, "Couldn't fetch the user's tags", it) }
                )
            }
        })
    }

    private fun addTagToUser(
        currentTags: List<TagUser>?,
        tag: Tag
    ) {
        val userHasTag = currentTags?.find { uTag -> uTag.tag.id == tag.id }
        if (userHasTag == null) {
            val userTag = TagUser.builder().tag(tag).user(currentUser).build()
            Amplify.API.mutate(
                ModelMutation.create(userTag),
                { response ->
                    currentUser.tags.add(response.data)
                    Log.i(TAG, currentUser.tags.toString())
                    progressLiveData.postValue("Asigned ${tag.name} to user")
                },
                { error ->
                    Log.e(TAG, "Couldn't assign ${tag.name} to ${currentUser.name}", error)
                })
        } else {
            progressLiveData.postValue("AddTagToUser | User already has tag ${tag.name}")
        }
    }

    private fun observeGroupsList(view: View) {
        InitialSetupData.groupsList.observe(viewLifecycleOwner, { groupNamesList ->
            toggleGroupsContentUI(view, groupNamesList.isNotEmpty())
            groupNamesList.forEach {
                val chip = addSelectedChip(it.name, view.cgSelectedGroups)
                chip?.setOnCloseIconClickListener { chp ->
                    InitialSetupData.removeGroup(chip.text.toString())
                    view.cgSelectedGroups.removeView(chp)
                }
            }
        })
    }

    private fun observeTagsList(view: View) {
        InitialSetupData.tagsList.observe(viewLifecycleOwner, { tagNamesList ->
            view.cgSelectedTags.removeAllViews()
            if (tagNamesList.isNotEmpty()) {
                view.cgSelectedTags.visibility = View.VISIBLE
                view.emptyTagSelection.visibility = View.GONE
                tagNamesList.forEach {
                    val chip = addSelectedChip(it, view.cgSelectedTags)
                    chip?.setOnCloseIconClickListener { chp ->
                        InitialSetupData.removeTag(chip.text.toString())
                        view.cgSelectedTags.removeView(chp)
                    }
                }
            } else {
                view.cgSelectedTags.visibility = View.GONE
                view.emptyTagSelection.visibility = View.VISIBLE
            }
        })
    }

    private fun findTag(name: String): MutableLiveData<Tag?> {
        val tag = MutableLiveData<Tag?>()
        Amplify.API.query(ModelQuery.list(Tag::class.java, Tag.NAME.contains(name)),
            {
                var tagFound: Tag? = null
                if (it.data.items.count() != 0) {
                    tagFound = it.data.items.first { item -> item.status == TagStatus.ALLOWED }
                }
                tag.postValue(tagFound)
            },
            {
                tag.postValue(null)
                Log.e(TAG, "Couldn't fetch tag", it)
            }
        )
        return tag
    }

    private fun createTag(name: String) {
        // creates or finds the tag that will be added to the user and posts it to the tagsToAddLiveData
        findTag(name).observe(viewLifecycleOwner, {
            if (it != null) {
                val userHasTag = currentUser.tags?.find { uTag -> uTag.tag.id == it.id } != null
                if (userHasTag) {
                    progressLiveData.postValue("User already has tag $name")
                } else {
                    var updatedTag = Tag.builder()
                        .name(it.name)
                        .status(it.status)
                        .uses(it.uses + 1)
                        .id(it.id)
                        .build()
                    Amplify.API.mutate(ModelMutation.update(updatedTag), { response ->
                        updatedTag = response.data
                        assignTagToUser(updatedTag)
                    }, { error -> Log.e(TAG, "Couldn't updated the tag's uses", error) })
                }
            } else {
                val tagObj = Tag.builder().name(name).status(TagStatus.ALLOWED).uses(1).build()
                Amplify.API.mutate(ModelMutation.create(tagObj),
                    { response ->
                        val tag = response.data
                        assignTagToUser(tag)
                    },
                    { error ->
                        Log.e(TAG, "Couldn't create the Tag", error)
                    }
                )
            }
        })
    }

    private fun assignTagToUser(tag: Tag) {
        _tagsToAdd.add(tag)
        tagsToAddLiveData.postValue(_tagsToAdd)
    }

    private fun toggleGroupsContentUI(view: View, visible: Boolean) {
        val visibility = if (visible) View.VISIBLE else View.GONE
        val emptyGroupsMsg = if (visible) View.GONE else View.VISIBLE
        view.cgSelectedGroups.visibility = visibility
        view.emptyGroupSelection.visibility = emptyGroupsMsg
    }

    private fun addSelectedChip(name: String, chipGroup: ChipGroup): Chip? {
        val exists =
            chipGroup.checkedChipIds.any { id -> chipGroup.findViewById<Chip>(id).text == name }
        if (!exists) {
            val chip = layoutInflater.inflate(R.layout.selected_tag, chipGroup, false) as Chip
            chip.text = name
            chip.setOnCloseIconClickListener {
                InitialSetupData.setTagsList(InitialSetupData.tagsList.value!!.filter { tag -> tag != name }
                    .toList())
            }
            chipGroup.addView(chip)
            Log.i(TAG, "Created chip $name")
            return chip
        }
        return null
    }

    companion object {
        private const val TAG = "InitialSetupConfirmationFragment"
    }
}