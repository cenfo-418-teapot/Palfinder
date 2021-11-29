package com.example.palfinder.views.user.account

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
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
                view.rvGroups.adapter = GroupsRecyclerViewAdapter(groups)
            })
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
        private const val TAG = "InitialGroupSelectionFragment"
    }
}