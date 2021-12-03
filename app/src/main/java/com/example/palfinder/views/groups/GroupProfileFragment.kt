package com.example.palfinder.views.groups

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.amplifyframework.datastore.generated.model.Status
import com.example.palfinder.R
import com.example.palfinder.backend.services.GroupAdmin
import kotlinx.android.synthetic.main.fragment_group_edit.view.*
import kotlinx.android.synthetic.main.fragment_group_list.*
import kotlinx.android.synthetic.main.fragment_group_list.view.*
import kotlinx.android.synthetic.main.fragment_group_profile.*
import kotlinx.android.synthetic.main.fragment_group_profile.tvTitle
import kotlinx.android.synthetic.main.fragment_group_profile.view.*
import kotlinx.android.synthetic.main.group_item.*
import java.util.*

/**
 * A simple [Fragment] subclass.
 * Use the [GroupProfile.newInstance] factory method to
 * create an instance of this fragment.
 */
class GroupProfile : Fragment() {
    // TODO: Rename and change types of parameters
//    private var sharedGroup: GroupAdmin.GroupModel? = null
    lateinit var group: GroupAdmin.GroupModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
        view.nav_add_user.setOnClickListener{ setFocus(view,1) }
        view.nav_create_event.setOnClickListener{ setFocus(view, 2) }
        view.nav_share.setOnClickListener{ setFocus(view, 3) }
        getProfileData()
        return view
    }

    private fun setFocus(view: View, navOption: Int){
        when(navOption) {
            1 -> {
                underline_add_user.visibility = View.VISIBLE
                underline_create_event.visibility = View.INVISIBLE
                underline_share.visibility = View.INVISIBLE
            }
            2 -> {
                underline_add_user.visibility = View.INVISIBLE
                underline_create_event.visibility = View.VISIBLE
                underline_share.visibility = View.INVISIBLE
            }
            3 -> {
                underline_add_user.visibility = View.INVISIBLE
                underline_create_event.visibility = View.INVISIBLE
                underline_share.visibility = View.VISIBLE
            }
        }
    }


    fun getProfileData() {
        val model = ViewModelProvider(requireActivity()).get(GroupSharedViewModel::class.java)
        model.message.observe(viewLifecycleOwner, {
            this.group = it
            setGroupData()
            Log.d(TAG, "Group RECEIVED! " + group.name)
        })
    }

    private fun setGroupData() {
        tv_privacy_subtitle.text = group.status.toString()
            .replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }
        tvTitle.text = group.name
        val description: String = tv_group_description.text.toString()
        tv_group_description.text = description + " " + group.description
        val tempTags: MutableList<String> = mutableListOf()
        if(!group.tags.isNullOrEmpty()) {
            tv_group_tag_list.text =  "Tags: "
            group.tags!!.forEach {
                tempTags.add(it.tag.name)
            }
            group.tags!!.forEach {
                val tempText: String = tv_group_tag_list.text.toString()
                tv_group_tag_list.text = tempText + " " + it.tag.name
            }
        } else {
//            tv_group_tag_list.text =  ""
            tv_group_tag_list.text =  "Tags: "
            //test
            tempTags.add("Tag 1")
            tempTags.add("Tag 2")
            tempTags.add("Tag 3")
            var cont = 0
            tempTags.forEach {
                cont++
                var tempText: String = tv_group_tag_list.text.toString()
                if(cont > 1 && cont <= tempTags.size) tempText += ", "
                tv_group_tag_list.text = tempText  + " " + it
            }
        }
    }

    companion object {
        private const val TAG = "GroupProfileFragment"
    }
}