package com.example.palfinder.views.groups

import android.app.SearchManager
import android.content.ComponentName
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.ArrayAdapter
import android.widget.SearchView
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.ItemTouchHelper
import com.example.palfinder.R
import com.example.palfinder.backend.services.GroupAdmin
import com.example.palfinder.backend.services.GroupService
import kotlinx.android.synthetic.main.fragment_group_list.*
import kotlinx.android.synthetic.main.fragment_group_list.view.*

/**
 * A simple [Fragment] subclass.
 * Use the [GroupListFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class GroupListFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_group_list, container, false)
        view.nav_discover_groups.setOnClickListener{ setFocus(view,1) }
        view.nav_my_groups.setOnClickListener{ setFocus(view, 2) }
        view.nav_create_group.setOnClickListener{ setFocus(view, 3) }
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView(group_list)
        GroupService.updateGroups(true)
    }

    private fun setFocus(view: View, navOption: Int){
        when(navOption) {
            1 -> {
                underline_discover_groups.visibility = View.VISIBLE
                underline_my_groups.visibility = View.INVISIBLE
                underline_create_group.visibility = View.INVISIBLE
            }
            2 -> {
                underline_discover_groups.visibility = View.INVISIBLE
                underline_my_groups.visibility = View.VISIBLE
                underline_create_group.visibility = View.INVISIBLE
            }
            3 -> {
                underline_discover_groups.visibility = View.INVISIBLE
                underline_my_groups.visibility = View.INVISIBLE
                underline_create_group.visibility = View.VISIBLE
                Navigation.findNavController(view).navigate(R.id.action_groupListFragment_to_groupEditFragment)
            }
        }
    }

    private fun setupRecyclerView(recyclerView: RecyclerView) {
        // add a touch gesture handler to manager the swipe to delete gesture
//        val itemTouchHelper = ItemTouchHelper(SwipeCallback(this))
//        itemTouchHelper.attachToRecyclerView(recyclerView)
        // update individual cell when the Group data are modified
        GroupAdmin.groups().observe(viewLifecycleOwner, Observer<MutableList<GroupAdmin.GroupModel>> { groups ->
            Log.d(TAG, "Note observer received ${groups.size} groups")

            // let's create a RecyclerViewAdapter that manages the individual cells
            recyclerView.adapter = GroupsRecyclerViewAdapter(groups)
        })
    }


    companion object {
        private const val TAG = "GroupListFragment"
    }
}