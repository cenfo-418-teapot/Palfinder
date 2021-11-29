package com.example.palfinder.views.user.account

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.example.palfinder.R

class InitialTagSelectionFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_initial_tag_selection, container, false)
        requireActivity().findViewById<Button>(R.id.btnContinue).setOnClickListener {
            val vp = requireActivity().findViewById<ViewPager2>(R.id.vpInitAccount)
            vp.currentItem = vp.currentItem + 1
        }
        return view
    }

}
