package com.example.palfinder.views.groups

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.amplifyframework.api.graphql.model.ModelMutation
import com.amplifyframework.api.graphql.model.ModelQuery
import com.amplifyframework.core.Amplify
import com.amplifyframework.datastore.generated.model.Status
import com.amplifyframework.datastore.generated.model.Tag
import com.amplifyframework.datastore.generated.model.TagGroup
import com.amplifyframework.datastore.generated.model.TagStatus
import com.example.palfinder.R
import com.example.palfinder.backend.services.GroupAdmin
import com.example.palfinder.backend.services.GroupService
import kotlinx.android.synthetic.main.fragment_group_edit.*
import kotlinx.android.synthetic.main.fragment_group_edit.view.*
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.util.*

class GroupProfileEditFragment : Fragment() {

    private var noteImagePath : String? = null
    private var noteImage : Bitmap? = null
    lateinit var group: GroupAdmin.GroupModel
    private val _tagsToAdd = ArrayList<Tag>()
    private val _tagsAdded = ArrayList<TagGroup>()
    private var countTagsToAdd = MutableLiveData(0)
    private var tagsSelected = false
    private val tagsToAddLiveData = MutableLiveData<List<Tag>>()
    private lateinit var groupUpdated: MutableLiveData<Boolean>
    private var imageEdited = false
    private var dataRetrieved = false
    lateinit var model: GroupSharedViewModel

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
            goTo(view, R.id.action_groupProfileEditFragment_to_groupProfile)
        }
        view.captureImage.setOnClickListener {
            imageEdited = true
            val i = Intent(
                Intent.ACTION_GET_CONTENT,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI
            )
            startActivityForResult(i, SELECT_PHOTO)
        }
        // create rounded corners for the image
//        view.iv_image.shapeAppearanceModel = view.iv_image.shapeAppearanceModel
//            .toBuilder()
//            .setAllCorners(CornerFamily.ROUNDED, 150.0f)
//            .build()
        observeTagsToAdd(view)
        view.btnConfirm?.setOnClickListener {
            try {
                group = validForm(group.id)
                if (this.noteImagePath != null && imageEdited) {
                    group.imageName = UUID.randomUUID().toString()
                    group.image = this.noteImage

                    // asynchronously store the image (and assume it will work)
                    GroupService.storeImage(this.noteImagePath!!, group.imageName!!)
                }
                GroupService.updateGroup(group) { success ->
                    if(success) {
                        groupUpdated.postValue(true)
                    } else {
                        groupUpdated.postValue(false)
                    }
                }
            } catch (e: IllegalStateException) {
                Log.e(TAG, "Form Validation Failed", e)
            }
        }
        observeGroupSender()
        return view
    }

    private fun observeGroupSender() {
        if(!dataRetrieved){
            model = ViewModelProvider(requireActivity()).get(GroupSharedViewModel::class.java)
            model.message.observe(viewLifecycleOwner, {
                this.group = it
                fillGroupData()
                group.tags?.forEach{ groupTag ->
                    _tagsAdded.add(groupTag)
                }
                Log.d(TAG, "Group RECEIVED to edit! " + group.name)
                dataRetrieved = true
            })
        }
    }

    private fun fillGroupData() {
        etName.setText(group.name)
        etDescription.setText(group.description)
        etState.setText(group.status.toString()
            .replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() })
        tilState.isEnabled = false
        iv_image.setImageBitmap(group.image)
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

    private fun observeTagsToAdd(view: View) {
        countTagsToAdd.observe(viewLifecycleOwner, {
            if(it == 0 && groupUpdated.value!! && tagsSelected){
                group.tags = _tagsAdded.toList()
                model.sendMessage(this.group)
                goTo(view, R.id.action_groupProfileEditFragment_to_groupProfile)
            }

        })
        groupUpdated = MutableLiveData(false)
        groupUpdated.observe(viewLifecycleOwner, {
            if(groupUpdated.value!!) {
                val countTags = GroupAdditionalSetUp.tagsList.value?.size
                if(countTags == null || countTags == 0) {
                    Toast.makeText(
                        activity,
                        "Group " + group.name + " updated",
                        Toast.LENGTH_SHORT
                    ).show()
                    model.sendMessage(this.group)
                    goTo(view, R.id.action_groupProfileEditFragment_to_groupProfile)
                } else {
                    tagsSelected = true
                    countTagsToAdd.postValue(countTags)
                    GroupAdditionalSetUp.tagsList.value?.forEach {
                        createTag(it, group)
                    }
                }
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
        val groupTag = currentTags?.find { uTag -> uTag.tag.id == tag.id }
        if (groupTag == null) {
            val tmpTag = TagGroup.builder().tag(tag).group(group.data).build()
            Amplify.API.mutate(
                ModelMutation.create(tmpTag),
                { response ->
//                    group.tags.add(response.data)
                    _tagsAdded.add(response.data)
                    Log.i(TAG, group.tags.toString())
//                    progressLiveData.postValue("Tag ${tag.name} asigned to group")
                    Log.i(TAG,"Tag ${tag.name} asigned to group")
                },
                { error ->
                    Log.e(TAG, "Couldn't assign ${tag.name} to ${group.name}", error)
                    //            Toast.makeText(
//                activity,
//                "Cannot update the group " + group.name + " right now",
//                Toast.LENGTH_SHORT
//            ).show()
                })
        } else {
//            progressLiveData.postValue("AddTagToGroup | Group already has tag ${tag.name}")
            Log.i(TAG,"AddTagToGroup | Group already has tag ${tag.name}")
        }
    }

    private fun findTag(name: String): MutableLiveData<Tag?> {
        val tag = MutableLiveData<Tag?>()
        Amplify.API.query(
            ModelQuery.list(Tag::class.java, Tag.NAME.contains(name)),
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
//                    progressLiveData.postValue("Group already has tag $name")
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
                Amplify.API.mutate(
                    ModelMutation.create(tagObj),
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

    private fun validForm(idGroup: String): GroupAdmin.GroupModel {
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
//        check(noteImagePath != null) { "Image no selected" }
        var tmpGroup = group
        tmpGroup.name = name
        tmpGroup.description = description
        tmpGroup.status = finalStatus
        return tmpGroup
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
        private const val TAG = "GroupProfileEditFragment"
        private const val SELECT_PHOTO = 100
    }
}