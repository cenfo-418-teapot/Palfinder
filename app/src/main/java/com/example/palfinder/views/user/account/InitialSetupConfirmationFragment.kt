package com.example.palfinder.views.user.account

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.viewpager2.widget.ViewPager2
import com.example.palfinder.R

class InitialSetupConfirmationFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_initial_setup_confirmation, container, false)
        requireActivity().findViewById<Button>(R.id.btnOmit).setOnClickListener {
            nextPage(requireActivity().findViewById(R.id.vpInitAccount))
        }
        requireActivity().findViewById<Button>(R.id.btnOmit).visibility = View.GONE
        return view
    }

    private fun nextPage(vp: ViewPager2) {
        vp.currentItem = vp.currentItem+1
    }

    companion object {
        private const val TAG = "InitialSetupConfirmationFragment"
    }
}