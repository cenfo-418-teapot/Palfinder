package com.example.palfinder.views.user

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.example.palfinder.R
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
