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
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.amplifyframework.datastore.generated.model.Status
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
    var imageEdited = false

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

        view.btnConfirm?.setOnClickListener {
            try {
                val tempGroup = validForm(group.id)
                if (this.noteImagePath != null && imageEdited) {
                    tempGroup.imageName = UUID.randomUUID().toString()
                    tempGroup.image = this.noteImage

                    // asynchronously store the image (and assume it will work)
                    GroupService.storeImage(this.noteImagePath!!, tempGroup.imageName!!)
                }
                GroupService.updateGroup(tempGroup)
                goTo(view, R.id.action_groupProfileEditFragment_to_groupListFragment)
            } catch (e: IllegalStateException) {
                Log.e(TAG, "Form Validation Failed", e)
            }
        }
        getProfileData()
        return view
    }

    fun getProfileData() {
        val model = ViewModelProvider(requireActivity()).get(GroupSharedViewModel::class.java)
        model.message.observe(viewLifecycleOwner, {
            this.group = it
            fillGroupData()
            Log.d(TAG, "Group RECEIVED! " + group.name)
        })
    }

    private fun fillGroupData() {
        // TODO: Fill up Group Data"
        etName.setText(group.name)
        etDescription.setText(group.description)
        etState.setText(group.status.toString().capitalize())
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
        return GroupAdmin.GroupModel(
            idGroup,
            name,
            description,
            null,
            null,
            null,
            finalStatus,
            group.imageName)
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
        //        private const val COUNTRIES: String =  {
//            "Belgium", "France", "Italy", "Germany", "Spain"
//        };
        private const val TAG = "GroupProfileEditFragment"
        private const val SELECT_PHOTO = 100
//        /**
//         * Use this factory method to create a new instance of
//         * this fragment using the provided parameters.
//         *
//         * @param param1 Parameter 1.
//         * @param param2 Parameter 2.
//         * @return A new instance of fragment GroupEditFragment.
//         */
//        @JvmStatic
//        fun newInstance(param1: String, param2: String) =
//            GroupEditFragment().apply {
//                arguments = Bundle().apply {
//                    putString(ARG_PARAM1, param1)
//                    putString(ARG_PARAM2, param2)
//                }
//            }
    }
}