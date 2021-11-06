package com.example.palfinder.views.user

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.example.palfinder.R
import kotlinx.android.synthetic.main.sign_in_fragment.*
import kotlinx.android.synthetic.main.sign_in_fragment.view.*

class SignInFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = layoutInflater.inflate(R.layout.sign_in_fragment, container, false)
        view.btnSignUp?.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.from_signIn_to_signUp)
        }
        return view
    }
}
