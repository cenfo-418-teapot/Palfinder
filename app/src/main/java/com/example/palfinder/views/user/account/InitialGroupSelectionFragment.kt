package com.example.palfinder.views.user.account

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
import com.example.palfinder.backend.services.InitialSetupData
import kotlinx.android.synthetic.main.fragment_initial_group_selection.view.*
import kotlin.streams.toList

class InitialGroupSelectionFragment : Fragment() {
    private var groupsList: MutableList<GroupAdmin.GroupModel> = mutableListOf()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_initial_group_selection, container, false)
        view.rvGroups.visibility = View.GONE
        GroupService.updateGroups(true)
        GroupAdmin.groups()
            .observe(viewLifecycleOwner, { groups ->
                Log.d(TAG, "Note observer received ${groups.size} groups")
                // let's create a RecyclerViewAdapter that manages the individual cells
                InitialSetupData.setAllGroupsList(groups.toList())
            })
        InitialSetupData.allGroupsList.observe(viewLifecycleOwner, {
            this.groupsList.clear()
            this.groupsList.addAll(it.toMutableList())
            view.rvGroups.adapter = GroupsAdapter(this.groupsList)
            view.rvGroups.adapter?.notifyDataSetChanged()
            view.rvGroups.visibility = View.VISIBLE
            view.progressBar.visibility = View.GONE
        })
        InitialSetupData.groupsList.observe(viewLifecycleOwner, {
            if (InitialSetupData.allGroupsList.value != null) {
                var tmp = InitialSetupData.allGroupsList.value!!.stream()
                    .filter { group -> !it.contains(group) }.toList().toMutableList()
                this.groupsList.clear()
                this.groupsList.addAll(tmp)
                Log.i(TAG, this.groupsList.toString())
                val noDataAvailable = view.etSearchData.text?.isBlank() == true && this.groupsList.isEmpty()
                toggleResultsUI(view, !noDataAvailable)
                view.rvGroups.adapter?.notifyDataSetChanged()
            }
        })
        requireActivity().findViewById<Button>(R.id.btnContinue).setOnClickListener {
            val vp = requireActivity().findViewById<ViewPager2>(R.id.vpInitAccount)
            vp.currentItem = vp.currentItem + 1
        }
        return view
    }

    private fun toggleResultsUI(view: View, visible: Boolean) {
        val visibility = if (visible) View.VISIBLE else View.GONE
        val msgVisibility =  if (visible) View.GONE else View.VISIBLE
        view.tilSearchData.visibility = visibility
        view.rvGroups.visibility = visibility
        view.llNoData.visibility = msgVisibility
    }

    companion object {
        private const val TAG = "InitialGroupSelectionFragment"
    }
}