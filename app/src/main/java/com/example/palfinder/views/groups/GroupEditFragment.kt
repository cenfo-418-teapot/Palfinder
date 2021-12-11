package com.example.palfinder.views.groups

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import androidx.navigation.Navigation
import com.amplifyframework.api.graphql.model.ModelMutation
import com.amplifyframework.api.graphql.model.ModelQuery
import com.amplifyframework.core.Amplify
import com.amplifyframework.datastore.generated.model.*
import com.example.palfinder.R
import com.example.palfinder.backend.services.GroupAdmin
import com.example.palfinder.backend.services.GroupService
import com.example.palfinder.backend.services.InitialSetupData
import com.example.palfinder.backend.services.UserData
import com.example.palfinder.components.OnIdListChange
import com.example.palfinder.views.user.account.InitialSetupConfirmationFragment
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_group_edit.*
import kotlinx.android.synthetic.main.fragment_group_edit.iv_image
import kotlinx.android.synthetic.main.fragment_group_edit.view.*
import kotlinx.android.synthetic.main.fragment_group_edit.view.btnCancel
import kotlinx.android.synthetic.main.fragment_initial_group_selection.*
import kotlinx.android.synthetic.main.fragment_initial_setup_confirmation.view.*
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.util.*

/**
 * A simple [Fragment] subclass.
 * Use the [GroupEditFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class GroupEditFragment : Fragment(), OnIdListChange {
    private val progressLiveData = MutableLiveData<String>()
    private var currentUser = UserData.currentUser.value!!
    private var noteImagePath : String? = null
    private var noteImage : Bitmap? = null
    private var _finishedAdditions = 0
    private lateinit var progressBar: ProgressBar
    private val _tagsToAdd = ArrayList<Tag>()
    private val tagsToAddLiveData = MutableLiveData<List<Tag>>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onIdListChange(list: List<String>) {
        GroupAdditionalSetUp.setTagsList(list)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_group_edit, container, false)
        view.btnCancel?.setOnClickListener {
            goTo(view, R.id.action_groupEditFragment_to_groupListFragment)
        }
        view.captureImage.setOnClickListener {
            val i = Intent(
                Intent.ACTION_GET_CONTENT,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI
            )
            startActivityForResult(i, SELECT_PHOTO)
        }
        progressBar = requireActivity().findViewById(R.id.progressBar3)
        _finishedAdditions++
        progressBar.progress = _finishedAdditions

//        progressLiveData.observe(viewLifecycleOwner, { action ->
//            Log.i(TAG, "Finished an action | $action")
//
//        })
        observeTagsToAdd()
        view.btnConfirm?.setOnClickListener {
            try {
                val group = validForm()
                if (this.noteImagePath != null) {
                    group.imageName = UUID.randomUUID().toString()
                    group.image = this.noteImage

                    // asynchronously store the image (and assume it will work)
                    GroupService.storeImage(this.noteImagePath!!, group.imageName!!)
                }
                GroupService.createGroup(group)
                GroupAdditionalSetUp.tagsList.value?.forEach { createTag(it) }
                goTo(view, R.id.action_groupEditFragment_to_groupListFragment)
            } catch (e: IllegalStateException) {
                Log.e(TAG, "Form Validation Failed", e)
            }
        }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val statusArray = resources.getStringArray(R.array.status)
        val adapter =  ArrayAdapter(
            requireActivity() as Context,
            R.layout.support_simple_spinner_dropdown_item,
            statusArray
        )
        etState.setAdapter(adapter)
    }
    private fun goTo(tmpView: View, tmpIdElement: Int) {
        Navigation.findNavController(tmpView).navigate(tmpIdElement)
    }

//    private fun observeGroupsToAdd() {
//        groupToAddLiveData.observe(viewLifecycleOwner, { groupToAdd ->
//            if (groupToAdd != null) {
//                Amplify.API.query(
//                    ModelQuery.get(User::class.java, currentUser.id),
//                    {
//                        UserData.setCurrentUser(it.data)
//                        currentUser = it.data
//                        addGroupToUser(
//                            currentUser.groups,
//                            groupToAdd
//                        )
//                    },
//                    { Log.e(TAG, "Couldn't fetch the user's groups", it) })
//            } else Log.i(TAG, "No groups were selected before saving changes")
//        })
//    }
//
//    private fun addGroupToUser(currentGroups: List<GroupMembers>?, group: GroupAdmin.GroupModel) {
//        val userIsMember = currentGroups?.find { member -> member.group.id == group.id }
//        if (userIsMember != null) {
//            progressLiveData.postValue("User was already a member of: ${group.name}")
//        } else {
//            val member = GroupMembers.builder().user(currentUser).group(group.data).build()
//            Amplify.API.mutate(
//                ModelMutation.create(member),
//                {
//                    currentUser.groups.add(it.data)
//                    progressLiveData.postValue("${currentUser.username} added to group ${group.name}")
//                },
//                { Log.e(TAG, "${currentUser.username} was not added as a member to ${group.name}") }
//            )
//        }
//    }

    private fun observeTagsToAdd() {
        tagsToAddLiveData.observe(viewLifecycleOwner, { tagsToAdd ->
            if (tagsToAdd.size == GroupAdditionalSetUp.tagsList.value!!.size) {
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
                    progressLiveData.postValue("Tag ${tag.name} asigned to user")
                },
                { error ->
                    Log.e(TAG, "Couldn't assign ${tag.name} to ${currentUser.name}", error)
                })
        } else {
            progressLiveData.postValue("AddTagToUser | User already has tag ${tag.name}")
        }
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

    private fun validForm(): GroupAdmin.GroupModel {
        val name = etName.text.toString()
        val description = etDescription.text.toString()
        val status = etState.text.toString()
        var finalStatus = Status.PUBLIC
        val requiredErrorMsg = "This field is required"
        resetInputErrorMsg()
        name.ifBlank { tilGroupName?.error = requiredErrorMsg }
        description.ifBlank { tilDescription?.error = requiredErrorMsg }
        if (this.noteImagePath == null) {
            tilState?.error = requiredErrorMsg
        }
        if (status.isBlank()) {
            tilState?.error = requiredErrorMsg
        } else {
            finalStatus = Status.valueOf(status.uppercase())
        }

        check(name.isNotBlank()) { "Name is blank" }
        check(description.isNotBlank()) { "Description is blank" }
        check(status.isNotBlank()) { "Status is blank" }
        return GroupAdmin.GroupModel(
            UUID.randomUUID().toString(),
            name,
            description,
            null,
            null,
            null,
            finalStatus)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, imageReturnedIntent: Intent?) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent)
        Log.d(TAG, "Select photo activity result : $imageReturnedIntent")
        when (requestCode) {
            SELECT_PHOTO -> if (resultCode == AppCompatActivity.RESULT_OK) {
                val selectedImageUri : Uri? = imageReturnedIntent!!.data

                // read the stream to fill in the preview
                var imageStream: InputStream? = activity?.contentResolver?.openInputStream(selectedImageUri!!)
                val selectedImage = BitmapFactory.decodeStream(imageStream)
                val ivPreview: ImageView = iv_image
                ivPreview.setImageBitmap(selectedImage)

                // store the image to not recreate the Bitmap every time
                this.noteImage = selectedImage

                // read the stream to store to a file
                imageStream = activity?.contentResolver?.openInputStream(selectedImageUri!!)
                val tempFile = File.createTempFile("image", ".image")
                copyStreamToFile(imageStream!!, tempFile)

                // store the path to create a note
                this.noteImagePath = tempFile.absolutePath

                Log.d(TAG, "Selected image : ${tempFile.absolutePath}")
            }
        }
    }


    private fun resetInputErrorMsg() {
        tilGroupName?.error = null
        tilDescription?.error = null
        tilState?.error = null
        tilImage?.error = null
    }

    private fun copyStreamToFile(inputStream: InputStream, outputFile: File) {
        inputStream.use { input ->
            val outputStream = FileOutputStream(outputFile)
            outputStream.use { output ->
                val buffer = ByteArray(4 * 1024) // buffer size
                while (true) {
                    val byteCount = input.read(buffer)
                    if (byteCount < 0) break
                    output.write(buffer, 0, byteCount)
                }
                output.flush()
                output.close()
            }
        }
    }

    companion object {
        private const val TAG = "GroupEditFragment"
        private const val SELECT_PHOTO = 100
    }
}