package com.example.palfinder.views.events

import android.icu.text.SimpleDateFormat
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.amplifyframework.datastore.generated.model.EventMembers
import com.amplifyframework.datastore.generated.model.EventRoles
import com.example.palfinder.R
import com.example.palfinder.backend.services.event.EventData
import com.example.palfinder.views.search.UserItemAdapter
import kotlinx.android.synthetic.main.fragment_event_detail1.view.*
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle


class eventAdminList : Fragment() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_event_admin, container, false)
        //initRecyclerView(view.rvSearch)
        EventData.currentEvent.observe(viewLifecycleOwner, {

            for (item in it.members!!){
        if (item.role.equals(EventRoles.ADMIN)){

        }
    }
       view.rvParticipants.adapter = UserItemAdapter(it.members!!.map { eventMembers -> eventMembers.user  })
        })



        return view
    }

    private fun initRecyclerView(rv: RecyclerView) {
        rv.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        rv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
    }

    companion object {
        private const val TAG = "SearchUserFragment"
    }
}