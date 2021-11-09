package com.example.palfinder.views.auth.recover.password

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.palfinder.R
import kotlinx.android.synthetic.main.fragment_get_recovery_code.view.*

class GetRecoveryCodeFragment : Fragment() {

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
        return view
    }
}