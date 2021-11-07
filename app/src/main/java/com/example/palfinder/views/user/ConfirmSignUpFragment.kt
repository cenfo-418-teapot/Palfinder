package com.example.palfinder.views.user

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.example.palfinder.R
import kotlinx.android.synthetic.main.confirm_sign_up_fragment.view.*
import kotlinx.android.synthetic.main.sign_up_fragment.view.*
import kotlinx.android.synthetic.main.sign_up_fragment.view.btnCancel

class ConfirmSignUpFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.confirm_sign_up_fragment, container, false)
        view.btnCancel?.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.from_confirmSignUp_to_signUp)
        }
        view.btnConfirm?.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.from_confirmSignUp_to_signIn)
        }
        return view
    }

    companion object {
        private const val TAG = "ConfirmSignUpFragment"
    }
}