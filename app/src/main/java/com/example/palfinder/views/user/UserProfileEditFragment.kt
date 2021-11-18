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
import kotlinx.android.synthetic.main.fragment_sign_up.*
import kotlinx.android.synthetic.main.fragment_user_profile_detail.view.*
import kotlinx.android.synthetic.main.fragment_user_profile_detail.view.user_profile_edit_cta
import kotlinx.android.synthetic.main.fragment_user_profile_edit.view.*

class UserProfileEditFragment : Fragment() {
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = layoutInflater.inflate(R.layout.fragment_user_profile_edit, container, false)
        view.user_profile_edit_back_button?.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_userProfileEditFragment_to_userProfileDetailFragment2)
        }
//
        return view
    }
}
