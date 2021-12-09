package com.example.palfinder.views.events

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.palfinder.R
import com.example.palfinder.backend.services.GroupService
import com.example.palfinder.backend.services.event.EventAPI
import com.example.palfinder.views.groups.GroupSwipeCallback
import com.example.palfiner.backend.services.event.EventManager
import kotlinx.android.synthetic.main.fragment_event_list.*
import kotlinx.android.synthetic.main.fragment_group_list.*

class EventListFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_event_list, container, false)
        return view

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
         setupRecyclerView(event_list)
        EventAPI.updateEvents()
    }

    private fun setupRecyclerView(recyclerView: RecyclerView) {
      /*  // add a touch gesture handler to manager the swipe to delete gesture
        val itemTouchHelper = ItemTouchHelper(GroupSwipeCallback(activity as AppCompatActivity))
        itemTouchHelper.attachToRecyclerView(recyclerView)
*/
        // update individual cell when the Group data are modified
        EventManager.events().observe(viewLifecycleOwner, Observer<MutableList<EventManager.EventModel>> { events ->
            Log.d(TAG, "Note observer received ${events.size} groups")

            // let's create a RecyclerViewAdapter that manages the individual cells
            recyclerView.adapter = EventsAdapter(events)
            if(events.size > 0) msgNoEvents.visibility = View.GONE
            else msgNoEvents.visibility = View.VISIBLE
        })
    }

    companion object {
        private const val TAG = "Event List Fragment "
    }

}