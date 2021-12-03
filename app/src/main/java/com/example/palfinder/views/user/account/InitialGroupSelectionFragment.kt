package com.example.palfinder.views.user.account

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.example.palfinder.R
import com.example.palfinder.backend.services.GroupAdmin
import com.example.palfinder.backend.services.GroupService
import com.example.palfinder.views.groups.GroupsRecyclerViewAdapter
import kotlinx.android.synthetic.main.fragment_initial_group_selection.view.*

class InitialGroupSelectionFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_initial_group_selection, container, false)
        GroupService.updateGroups()
        GroupAdmin.groups()
            .observe(viewLifecycleOwner, { groups ->
                Log.d(TAG, "Note observer received ${groups.size} groups")
                // let's create a RecyclerViewAdapter that manages the individual cells
                view.rvGroups.adapter = GroupsRecyclerViewAdapter(groups, null)
            })
        requireActivity().findViewById<Button>(R.id.btnContinue).setOnClickListener {
            val vp = requireActivity().findViewById<ViewPager2>(R.id.vpInitAccount)
            vp.currentItem = vp.currentItem + 1
        }
        return view
    }

    companion object {
        private const val TAG = "InitialGroupSelectionFragment"
    }
}