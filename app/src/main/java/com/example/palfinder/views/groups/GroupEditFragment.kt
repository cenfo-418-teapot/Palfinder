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
import android.widget.ImageView
import android.widget.Toast
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
import com.example.palfinder.backend.services.UserData
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_group_edit.*
import kotlinx.android.synthetic.main.fragment_group_edit.iv_image
import kotlinx.android.synthetic.main.fragment_group_edit.view.*
import kotlinx.android.synthetic.main.fragment_group_edit.view.btnCancel
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.util.*

/**
 * A simple [Fragment] subclass.
 * Use the [GroupEditFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class GroupEditFragment : Fragment() {
    private val progressLiveData = MutableLiveData<String>()
    private var currentUser = UserData.currentUser.value!!
    private var noteImagePath : String? = null
    private var noteImage : Bitmap? = null
    private val _tagsToAdd = ArrayList<Tag>()
    private var countTagsToAdd = MutableLiveData(0)
    private var tagsSelected = false
    private val tagsToAddLiveData = MutableLiveData<List<Tag>>()
    private lateinit var group: GroupAdmin.GroupModel
    private lateinit var groupCreated: MutableLiveData<Boolean>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_group_edit, container, false)
        view.btnCancel?.setOnClickListener {
            backToProfile(view)
        }
        view.captureImage.setOnClickListener {
            val i = Intent(
                Intent.ACTION_GET_CONTENT,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI
            )
            startActivityForResult(i, SELECT_PHOTO)
        }

        observeTagsToAdd(view)
        view.btnConfirm?.setOnClickListener {
            try {
                group = validForm()
                if (this.noteImagePath != null) {
                    group.imageName = UUID.randomUUID().toString()
                    group.image = this.noteImage

                    // asynchronously store the image (and assume it will work)
                    GroupService.storeImage(this.noteImagePath!!, group.imageName!!)
                }
                GroupService.createGroup(group) { success ->
                    if(success) {
                        groupCreated.postValue(true)
                    } else {
                        Toast.makeText(
                            activity,
                            "Cannot create the group " + group.name + " right now",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

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

    private fun backToProfile(tmpView: View){
        Navigation.findNavController(tmpView).popBackStack()
    }

    private fun observeTagsToAdd(view: View) {
        countTagsToAdd.observe(viewLifecycleOwner, {
            if(it == 0 && groupCreated.value!! && tagsSelected) backToProfile(view)
        })
        groupCreated = MutableLiveData(false)
        groupCreated.observe(viewLifecycleOwner, {
            if(groupCreated.value!!) {
                tagsSelected = true
                countTagsToAdd.postValue(GroupAdditionalSetUp.tagsList.value?.size)
                GroupAdditionalSetUp.tagsList.value?.forEach {
                    createTag(it, group)
                }
                addGroupToUser(group)
            }
        })
        tagsToAddLiveData.observe(viewLifecycleOwner, { tagsToAdd ->
            if (tagsToAdd.size == GroupAdditionalSetUp.tagsList.value!!.size) {
                tagsToAdd.forEach { tag ->
                    addTagToGroup(group.tags, tag)
                }
            }
        })
    }

    private fun addTagToGroup(
        currentTags: List<TagGroup>?,
        tag: Tag
    ) {
        val groupTag = currentTags?.find { uTag -> uTag.tag.name == tag.name }
        if (groupTag == null) {
            val userTag = TagGroup.builder().tag(tag).group(group.data).build()
            Amplify.API.mutate(
                ModelMutation.create(userTag),
                { response ->
                    //group.tags.add(response.data)
                    Log.i(TAG, group.tags.toString())
                    progressLiveData.postValue("Tag ${tag.name} asigned to group")
                },
                { error ->
                    Log.e(TAG, "Couldn't assign ${tag.name} to ${group.name}", error)
                })
        } else {
            progressLiveData.postValue("AddTagToGroup | Group already has tag ${tag.name}")
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

    private fun createTag(name: String, group: GroupAdmin.GroupModel) {
        // creates or finds the tag that will be added to the user and posts it to the tagsToAddLiveData
        findTag(name).observe(viewLifecycleOwner, {
            if (it != null) {
                val groupHasTag = group.tags?.find { uTag -> uTag.tag.id == it.id } != null
                if (groupHasTag) {
                    progressLiveData.postValue("Group already has tag $name")
                    Log.d(TAG, "Group already has tag $name")
                } else {
                    var updatedTag = Tag.builder()
                        .name(it.name)
                        .status(it.status)
                        .uses(it.uses + 1)
                        .id(it.id)
                        .build()
                    Amplify.API.mutate(ModelMutation.update(updatedTag), { response ->
                        updatedTag = response.data
                        assignTagToGroup(updatedTag)
                        val tagsLeft = countTagsToAdd.value?.minus(1)
                        countTagsToAdd.postValue(tagsLeft)
                    }, { error -> Log.e(TAG, "Couldn't updated the tag's uses", error) })
                }
            } else {
                val tagObj = Tag.builder().name(name).status(TagStatus.ALLOWED).uses(1).build()
                Amplify.API.mutate(ModelMutation.create(tagObj),
                    { response ->
                        val tag = response.data
                        Log.d(TAG, "Tag created with id ${tag.id}")
                        assignTagToGroup(tag)
                        val tagsLeft = countTagsToAdd.value?.minus(1)
                        countTagsToAdd.postValue(tagsLeft)
                    },
                    { error ->
                        Log.e(TAG, "Couldn't create the Tag", error)
                    }
                )
            }
        })
    }

    private fun assignTagToGroup(tag: Tag) {
        _tagsToAdd.add(tag)
        tagsToAddLiveData.postValue(_tagsToAdd)
    }

    private fun addGroupToUser(group: GroupAdmin.GroupModel) {
        val member = GroupMembers.builder().role(GroupRoles.OWNER).user(currentUser).group(group.data).groupUsersId(group.id).build()
        Amplify.API.mutate(
            ModelMutation.create(member),
            {
                Log.e(TAG, "${currentUser.username} was added as a owner member to ${group.name}")
            },
            { Log.e(TAG, "${currentUser.username} was not added as a member to ${group.name}") }
        )
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