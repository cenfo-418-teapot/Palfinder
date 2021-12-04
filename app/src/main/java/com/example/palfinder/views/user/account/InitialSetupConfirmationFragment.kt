package com.example.palfinder.views.user.account

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import com.amplifyframework.api.graphql.model.ModelMutation
import com.amplifyframework.api.graphql.model.ModelQuery
import com.amplifyframework.core.Amplify
import com.amplifyframework.datastore.generated.model.Tag
import com.amplifyframework.datastore.generated.model.TagStatus
import com.amplifyframework.datastore.generated.model.TagUser
import com.amplifyframework.datastore.generated.model.User
import com.example.palfinder.R
import com.example.palfinder.backend.services.InitialSetupData
import com.example.palfinder.backend.services.UserData
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import kotlinx.android.synthetic.main.fragment_initial_setup_confirmation.view.*

class InitialSetupConfirmationFragment : Fragment() {
    private var currentUser = UserData.currentUser.value!!
    private val _tagsToAdd = ArrayList<Tag>()
    private val tagsToAddLiveData = MutableLiveData<List<Tag>>()
    private val progressLiveData = MutableLiveData<Int>()
    private var _finishedAdditions = 0
    private var _totalChanges = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_initial_setup_confirmation, container, false)
        progressLiveData.observe(viewLifecycleOwner, {
            Log.i(TAG, "Finished an action")
            _finishedAdditions++
            view.progressBar2.progress = _finishedAdditions
//            if (totalAdditions == _finishedAdditions) startActivity(Intent(activity, HomeActivity::class.java))
        })
        observeTagsList(view)
        observeGroupsList(view)
        observeTagsToAdd()
        requireActivity().findViewById<Button>(R.id.btnSave).setOnClickListener { _ ->
            val totalTagsToAdd = InitialSetupData.tagsList.value?.size ?: 0
            val totalGroupsToAdd = InitialSetupData.groupsList.value?.size ?: 0
            _totalChanges = totalTagsToAdd + totalGroupsToAdd
            if (view.progressBar2.visibility == View.GONE && _totalChanges > 0) {
                view.progressBar2.visibility = View.VISIBLE
                view.progressBar2.max = _totalChanges
            }
            InitialSetupData.tagsList.value?.forEach { createTag(it) }
        }
        return view
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
                    progressLiveData.postValue(1)
                },
                { error ->
                    Log.e(TAG, "Couldn't assign ${tag.name} to ${currentUser.name}", error)
                })
        } else {
            Log.i(TAG, "This user already has the tag ${tag.name}")
            progressLiveData.postValue(1)
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
                    Log.i(TAG, "User already has tag $name")
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