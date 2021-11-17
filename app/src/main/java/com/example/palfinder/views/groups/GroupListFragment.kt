package com.example.palfinder.views.groups

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.example.palfinder.R
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

    private fun loadData(){

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

    companion object {
        private const val TAG = "GroupListFragment"
    }
}