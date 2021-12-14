package com.example.palfinder.views.events

import android.content.Intent
import android.icu.text.SimpleDateFormat
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.amplifyframework.api.graphql.model.ModelQuery
import com.amplifyframework.core.Amplify
import com.amplifyframework.datastore.generated.model.Event
import com.example.palfinder.R
import com.example.palfinder.backend.services.UserData
import com.example.palfinder.backend.services.event.EventData
import com.example.palfinder.views.HomeActivity
import com.example.palfinder.views.search.FiltersModal
import com.example.palfinder.views.search.SearchEventsFragment
import com.example.palfinder.views.search.SearchGroupsFragment
import com.example.palfinder.views.search.SearchUsersFragment
import kotlinx.android.synthetic.main.activity_event_detail.*
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.activity_search.*
import kotlinx.android.synthetic.main.activity_search.bottom_navigation
import kotlinx.android.synthetic.main.fragment_event_detail.view.*
import kotlinx.android.synthetic.main.fragment_user_profile_detail.view.*
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

private const val ARG_ID = "id"

class EventDetailActivity : AppCompatActivity(R.layout.activity_event_detail) {

    private var  id :String? = null

    private val event = MutableLiveData<Event>()

    private val steps = arrayListOf(
        Pair(R.id.mnuUsers, SearchUsersFragment()),
        Pair(R.id.mnuEvents, SearchEventsFragment()),
        Pair(R.id.mnuGroups, SearchGroupsFragment()),
    )

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        EventData.currentEvent.observe(this, {
            eventTittle.text = it.name
            val pattern = "dd MMMM yyyy HH:mm a"
            val simpleDateFormat = SimpleDateFormat(pattern)
            val date: String = simpleDateFormat.format(it.`when`.toDate())
            evetDate.text = date.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL))
            eventDetailDescription.text = it.description
            Log.i("Event Mnager ", it.toString())
        })
    }


     @RequiresApi(Build.VERSION_CODES.O)
     fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_event_detail, container, false)
        val event = EventData.currentEvent.value
        Log.i("Event detail", event.toString())

        return view
    }

    fun get(id : String) {

        Amplify.API.query(
            ModelQuery.get(Event::class.java, id.toString()),
            { response ->
                Log.i("Event Mnager ", "Created")
                if (response.hasErrors()) {
                    Log.e("Event Mnager ", response.errors.first().message)
                } else {
                    Log.i("Event Mnager ", "Updated  Event with id: " + response.data.id)

                    if (response.hasData()) {
                        event.postValue(response.data as Event)
                    }
                }
            },
            { error ->
                Log.e("Event Mnager ", "Update failed", error)
            })

    }


    fun newInstance(id : String) =
        EventDetail().apply {
            arguments = Bundle().apply {
                putString(ARG_ID, id)
            }
        }

}

