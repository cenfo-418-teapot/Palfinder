package com.example.palfinder.views.user

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.example.palfinder.R
import com.example.palfinder.backend.services.AuthenticationService
import com.example.palfinder.backend.services.UserData
import com.example.palfinder.views.HomeActivity
import com.example.palfinder.views.auth.recover.password.RecoverPasswordActivity
import kotlinx.android.synthetic.main.fragment_sign_in.*
import kotlinx.android.synthetic.main.fragment_sign_in.etPassword
import kotlinx.android.synthetic.main.fragment_sign_in.etUsername
import kotlinx.android.synthetic.main.fragment_sign_in.view.*
import kotlinx.android.synthetic.main.fragment_sign_in.view.btnSignUp
import kotlinx.android.synthetic.main.fragment_sign_up.*
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
            Navigation.findNavController(view).navigate(R.id.action_userProfileDetailFragment2_to_userProfileEditFragment)
        }

        //        val user = UserData._currentUser.value.name
        view.test_string.text = user.email

//        Log.d("Test: ", user.description())
        
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
}