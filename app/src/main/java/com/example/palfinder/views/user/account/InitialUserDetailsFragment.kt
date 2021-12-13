package com.example.palfinder.views.user.account

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.viewpager2.widget.ViewPager2
import com.amplifyframework.core.Amplify
import com.amplifyframework.datastore.generated.model.User
import com.amplifyframework.datastore.generated.model.UserStatus
import com.example.palfinder.R
import com.example.palfinder.backend.services.InitialSetupData
import com.example.palfinder.backend.services.UserData
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import kotlinx.android.synthetic.main.fragment_initial_user_details.view.*

class InitialUserDetailsFragment : Fragment() {
    private val canContinue = MutableLiveData(false)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_initial_user_details, container, false)
        val tvName = view.tvName
        val tvLastName = view.tvLastName
        val inputValidationList: Array<Pair<TextInputEditText, TextInputLayout>> = arrayOf(
            Pair(tvName, view.tilName),
            Pair(tvLastName, view.tilLastName)
        )

        val btnContinue = requireActivity().findViewById<Button>(R.id.btnContinue)
        val vp = requireActivity().findViewById<ViewPager2>(R.id.vpInitAccount)
        canContinue.observe(viewLifecycleOwner, {
            btnContinue.isEnabled = it
            vp.isUserInputEnabled = it
        })
        observeRequiredFields(*inputValidationList)
        btnContinue.setOnClickListener {
            inputValidationList.forEach {
                it.second.error =
                    if (it.first.text.toString().isBlank()) "This field is required" else null
            }
            if (canContinue.value == true) {
                val vp = requireActivity().findViewById<ViewPager2>(R.id.vpInitAccount)
                InitialSetupData.setUser(getUserData(view))
                vp.currentItem = vp.currentItem + 1
            }
        }
        return view
    }

    private fun observeRequiredFields(vararg input: Pair<TextInputEditText, TextInputLayout>) {
        input.forEach {
            it.first.setOnEditorActionListener(TextView.OnEditorActionListener { et, actionId, _ ->
                if (actionId == EditorInfo.IME_ACTION_NEXT) {
                    canContinue.postValue(validateInputs(*input))
                    et.focusSearch(View.FOCUS_DOWN).requestFocus()
                    return@OnEditorActionListener true
                }
                false
            })
        }
    }

    private fun validateInputs(vararg input: Pair<TextInputEditText, TextInputLayout>): Boolean {
        // Returns true if there are no null or blank items found
        return !input.any { data -> data.first.text.toString().isBlank() }
    }

    private fun getUserData(view: View): User? {
        if (UserData.currentUser.value != null) {
            return User.builder()
                .email(UserData.currentUser.value!!.email)
                .username(Amplify.Auth.currentUser.username)
                .name(view.tvName.text.toString())
                .lastName(view.tvLastName.text.toString())
                .status(UserStatus.INCOMPLETE)
                .description(view.tvDescription.text.toString())
                .photo("")
                .phoneNumber("")
                .build()
        }
        return null
    }
}