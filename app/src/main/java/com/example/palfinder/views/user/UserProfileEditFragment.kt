package com.example.palfinder.views.user

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.amplifyframework.core.Amplify
import com.amplifyframework.api.graphql.model.ModelMutation
import com.amplifyframework.datastore.generated.model.User
import com.amplifyframework.datastore.generated.model.UserStatus
import com.example.palfinder.R
import com.example.palfinder.backend.services.UserData
import kotlinx.android.synthetic.main.fragment_user_profile_edit.*
import kotlinx.android.synthetic.main.fragment_user_profile_edit.view.*

class UserProfileEditFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = layoutInflater.inflate(R.layout.fragment_user_profile_edit, container, false)

        var id = UserData.currentUser.value!!.id
        val email = UserData.currentUser.value!!.email
        val username = UserData.currentUser.value!!.username
        val name = UserData.currentUser.value!!.name
        val lastName = UserData.currentUser.value!!.lastName
        val status = UserData.currentUser.value!!.status
        val description = UserData.currentUser.value!!.description
        val phone = UserData.currentUser.value!!.phoneNumber
        val photo = UserData.currentUser.value!!.photo

        view.user_name.setText(name)
        view.user_lastName.setText(lastName)
        view.user_bio.setText(description)

        val updatedName = view.user_name.text
        val updatedLastName = view.user_lastName.text
        val updatedBio = view.user_bio.text

        view.user_profile_edit_back_button?.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_userProfileEditFragment_to_userProfileDetailFragment2)
        }

        view.user_profile_edit_done_button?.setOnClickListener {
            Toast.makeText(context, "New name = $updatedName", Toast.LENGTH_SHORT).show()
            updateUserProfile(email, username, updatedName.toString(), updatedLastName.toString(), status, photo, phone, updatedBio.toString(), id)
//            Toast.makeText(context, "Hey there", Toast.LENGTH_SHORT).show()
//            Navigation.findNavController(view).navigate(R.id.action_userProfileEditFragment_to_userProfileDetailFragment2)
        }

        return view
    }

    private fun updateUserProfile(email: String, username: String, name: String, lastname: String, status: UserStatus, photo: String, phone: String, description: String, id: String) {
        val updatedUserProfile = User.builder().email(email).username(username).name(name).lastName(lastname).status(status).photo(photo).phoneNumber(phone).description(description).id(id).build()

        Amplify.API.mutate(
            ModelMutation.update(updatedUserProfile),
            { response ->
                Log.i("Palfinder App", "Update User Profile with id: " + response.data.id) },
            { error ->
                Log.e("Palfinder App", "Update fail", error) }
        )

        Toast.makeText(context, "test...!", Toast.LENGTH_SHORT).show()
    }

}
