package com.example.palfinder.backend.services

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import com.amplifyframework.api.graphql.model.ModelMutation
import com.amplifyframework.api.graphql.model.ModelQuery
import com.amplifyframework.core.Amplify
import com.amplifyframework.datastore.generated.model.Group
import com.amplifyframework.storage.StorageAccessLevel
import com.amplifyframework.storage.options.StorageDownloadFileOptions
import com.amplifyframework.storage.options.StorageRemoveOptions
import com.amplifyframework.storage.options.StorageUploadFileOptions
import java.io.File
import java.io.FileInputStream
import java.io.IOException
import java.lang.Exception

/**
 * Object for Groups administration service
 * to make graphql requests.
 * @author Isaac Miranda
 */
object GroupService {

    private const val TAG = "GroupService"

    fun updateGroups() {
        val groupList = GroupAdmin.groups().value
        val isEmpty = groupList?.isEmpty() ?: false

        // query notes when signed in and we do not have Notes yet
        if (isEmpty) {
            this.queryGroups()
        } else {
            GroupAdmin.resetGroups()
            this.queryGroups()
        }
    }

    fun queryGroups() {
        Log.i(TAG, "Querying groups")

        Amplify.API.query(
            ModelQuery.list(Group::class.java),
            { response ->
                Log.i(TAG, "Queried")
                for (groupData in response.data) {
                    Log.i(TAG, groupData.name)
                    // TODO should add all the groups at once instead of one by one (each add triggers a UI refresh)
                    GroupAdmin.addGroup(GroupAdmin.GroupModel.from(groupData))
                }
            },
            { error -> Log.e(TAG, "Query failure", error) }
        )
    }

    fun createGroup(groupModel : GroupAdmin.GroupModel) {
        Log.i(TAG, "Creating groups")

        Amplify.API.mutate(
            ModelMutation.create(groupModel.data),
            { response ->
                Log.i(TAG, "Created")
                if (response.hasErrors()) {
                    Log.e(TAG, response.errors.first().message)
                } else {
                    Log.i(TAG, "Created Group with id: " + response.data.id)
                }
            },
            { error -> Log.e(TAG, "Create failed", error) }
        )
    }

    fun createTagRelation() {
        // TODO: Write script for many to many new tag connection to the group
    }

    fun createUserRelation(){
        // TODO: Write script for many to many new user connection to the group
    }

    // NO createEventRelation because events belong to a group

    fun deleteGroup(tmpGroupModel : GroupAdmin.GroupModel?) {

        if (tmpGroupModel == null) return

        Log.i(TAG, "Deleting Group $tmpGroupModel")
        val data = tmpGroupModel.data
        Amplify.API.mutate(
            ModelMutation.delete(data),
            { response ->
                Log.i(TAG, "Deleted")
                if (response.hasErrors()) {
                    response.errors.iterator().forEach {
                        Log.e(TAG, it.message)
                    }
//                    throw IOException("You can't delete the group ${data.name}")
                } else {
                    Log.i(TAG, "Deleted Group $response")
                }
            },
            {
                error -> Log.e(TAG, "Delete failed", error)
//                throw IOException("You can't delete the group ${data.name}")
            }
        )
    }
    fun storeImage(filePath: String, key: String) {
        val file = File(filePath)
        val options = StorageUploadFileOptions.builder()
            .accessLevel(StorageAccessLevel.PRIVATE)
            .build()

        Amplify.Storage.uploadFile(
            key,
            file,
            options,
            { progress -> Log.i(TAG, "Fraction completed: ${progress.fractionCompleted}") },
            { result -> Log.i(TAG, "Successfully uploaded: " + result.key) },
            { error -> Log.e(TAG, "Upload failed", error) }
        )
    }

    fun deleteImage(key : String) {

        val options = StorageRemoveOptions.builder()
            .accessLevel(StorageAccessLevel.PRIVATE)
            .build()

        Amplify.Storage.remove(
            key,
            options,
            { result -> Log.i(TAG, "Successfully removed: " + result.key) },
            { error -> Log.e(TAG, "Remove failure", error) }
        )
    }

    fun retrieveImage(key: String, completed : (image: Bitmap) -> Unit) {
        val options = StorageDownloadFileOptions.builder()
            .accessLevel(StorageAccessLevel.PRIVATE)
            .build()

        val file = File.createTempFile("image", ".image")

        Amplify.Storage.downloadFile(
            key,
            file,
            options,
            { progress -> Log.i(TAG, "Fraction completed: ${progress.fractionCompleted}") },
            { result ->
                Log.i(TAG, "Successfully downloaded: ${result.file.name}")
                val imageStream = FileInputStream(file)
                val image = BitmapFactory.decodeStream(imageStream)
                completed(image)
            },
            { error -> Log.e(TAG, "Download Failure", error) }
        )
    }
}