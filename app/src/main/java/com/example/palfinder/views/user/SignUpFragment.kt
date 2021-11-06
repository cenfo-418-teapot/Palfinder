package com.example.palfinder.views.user

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.example.palfinder.R
import kotlinx.android.synthetic.main.sign_up_fragment.view.*

class SignUpFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = layoutInflater.inflate(R.layout.sign_up_fragment, container, false)
        view.btnCancel?.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.from_signUp_to_signIn)
        }
        Log.e(TAG, "On Create View")
        return view
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.e(TAG, "On Create")
    }
    companion object {
        private const val TAG = "SignUpFragment"
    }
}
