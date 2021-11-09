package com.example.palfinder.views.auth.recover.password

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import com.amplifyframework.auth.AuthException
import com.example.palfinder.R
import com.example.palfinder.backend.services.AuthenticationService
import kotlinx.android.synthetic.main.fragment_get_recovery_code.*
import kotlinx.android.synthetic.main.fragment_get_recovery_code.etUsername
import kotlinx.android.synthetic.main.fragment_get_recovery_code.tilUsername
import kotlinx.android.synthetic.main.fragment_get_recovery_code.view.*
import kotlinx.android.synthetic.main.sign_up_fragment.*

class GetRecoveryCodeFragment : Fragment() {
    lateinit var stepChange: OnStepChange
    private val _userFound = MutableLiveData<Boolean>()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        stepChange = context as OnStepChange
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _userFound.observe(this, {
            tilUsername?.error = if (!it) "That user does not exist" else null
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_get_recovery_code, container, false)
        view.etUsername?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun afterTextChanged(p0: Editable?) {}

            override fun onTextChanged(text: CharSequence?, start: Int, before: Int, count: Int) {
                view.btnSend?.isEnabled = text.toString().isNotBlank()
            }
        })
        view.tvHasCode?.setOnClickListener {
            stepChange.onStepChange(1)
        }
        view.btnSend?.setOnClickListener {
            AuthenticationService.PasswordReset.sendCode(
                etUsername.text.toString(),
                {
                    _userFound.postValue(true)
                    stepChange.onStepChange(1)
                }, {
                    when (it) {
                        is AuthException.UserNotFoundException -> _userFound.postValue(false)
                        else -> Log.e(TAG, "Password reset failed", it)
                    }
                }
            )
        }
        return view
    }
    companion object {
        private const val TAG = "GetRecoveryCodeFragment"
    }
}