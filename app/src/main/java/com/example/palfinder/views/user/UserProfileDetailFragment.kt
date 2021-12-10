package com.example.palfinder.views.user

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.amplifyframework.core.Amplify
import com.example.palfinder.R
import com.example.palfinder.backend.services.UserData
import com.example.palfinder.backend.services.UserService
import kotlinx.android.synthetic.main.fragment_user_profile_detail.view.*

class UserProfileDetailFragment : Fragment() {
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = layoutInflater.inflate(R.layout.fragment_user_profile_detail, container, false)

        view.user_profile_edit_cta?.setOnClickListener {
            Navigation.findNavController(view)
                .navigate(R.id.action_userProfileDetailFragment2_to_userProfileEditFragment)
        }
        UserService.getUserByUsername(Amplify.Auth.currentUser.username,
            {
                UserData.setCurrentUser(it.data.items.first())
            },
            { Log.e(TAG, "error getting user data", it) })

        UserData.currentUser.observe(viewLifecycleOwner, {
            val fullName ="${it.name} ${it.lastName}"
            val username = "@${it.username}"
            view.user_name.text = fullName
            view.user_description.text = it.description
            view.user_username.text = username
        })


//        Log.i("User Profile", "User Data: " + UserData.currentUser.toString())

//        view.btnSignIn?.setOnClickListener {
//            signIn()
//        }
//        UserData.isSignedIn.observe(viewLifecycleOwner) {
//            if (it) {
//                startActivity(Intent(activity, HomeActivity::class.java))
//            } else {
//                tvErrorMsg.visibility = View.VISIBLE
//                tvErrorMsg.text = resources.getString(R.string.wrong_login_credentials)
//            }
//        }
//        view.tvRecoverPassword?.setOnClickListener {
//            startActivity(Intent(context, RecoverPasswordActivity::class.java))
//        }
        return view
    }

//    override fun onResume() {
//        super.onResume()
//        tvErrorMsg?.visibility = View.INVISIBLE
//    }

    //    private fun signIn() {
//        val username = etUsername.text.toString()
//    }
    companion object {
        private const val TAG = "UserProfileDetailFragment"
    }
}