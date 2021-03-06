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
import androidx.lifecycle.MutableLiveData
import androidx.navigation.navGraphViewModels
import com.amplifyframework.api.graphql.model.ModelQuery
import com.amplifyframework.core.Amplify
import com.amplifyframework.datastore.generated.model.Event
import com.amplifyframework.datastore.generated.model.User
import com.example.palfinder.R
import com.example.palfinder.backend.services.event.EventAPI
import com.example.palfinder.views.search.UserItemAdapter
import com.example.palfiner.backend.services.event.EventManager
import kotlinx.android.synthetic.main.event_item.view.*
import kotlinx.android.synthetic.main.fragment_event_create.view.*
import kotlinx.android.synthetic.main.fragment_event_detail.view.*
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

private const val ARG_ID = "id"

class EventDetail : Fragment(){

    private var  id :String? = null

    private val event = MutableLiveData<Event>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            id = it.getString(ARG_ID)
            Log.i("Prueba", id.toString())
        }



        get(id.toString())
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_event_detail, container, false)

        event.observe(viewLifecycleOwner, { event ->
            view.eventTittle.text = event.name
            val pattern = "dd MMMM yyyy HH:mm a"
            val simpleDateFormat = SimpleDateFormat(pattern)
            val date: String = simpleDateFormat.format(event.`when`.toDate())
           view.evetDate.text = date.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL))
            view.eventDetailDescription.text = event.description
            Log.i("Event Mnager ", event.toString())
        })

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



companion object{



    fun newInstance(id : String) =
        EventDetail().apply {
            arguments = Bundle().apply {
                putString(ARG_ID, id)
            }
        }

}
    }


