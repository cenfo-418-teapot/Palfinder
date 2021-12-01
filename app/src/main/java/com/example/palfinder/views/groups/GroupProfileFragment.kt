package com.example.palfinder.views.groups

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.example.palfinder.R
import com.example.palfinder.backend.services.GroupAdmin
import kotlinx.android.synthetic.main.fragment_group_edit.view.*
import kotlinx.android.synthetic.main.fragment_group_profile.view.*
/**
 * A simple [Fragment] subclass.
 * Use the [GroupProfile.newInstance] factory method to
 * create an instance of this fragment.
 */
class GroupProfile : Fragment() {
    // TODO: Rename and change types of parameters
    private var sharedGroup: GroupAdmin.GroupModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val model = ViewModelProvider(requireActivity()).get(GroupSharedViewModel::class.java)
        model.message.observe(viewLifecycleOwner, Observer {
            sharedGroup = it
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_group_profile, container, false)
        view.tv_grup_view_all_members?.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_groupProfile_to_groupUsersFragment)
        }
        return view
    }

    companion object {

    }
}