package com.example.palfinder.views.events

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.amazonaws.util.DateUtils
import com.amplifyframework.core.model.temporal.Temporal
import com.amplifyframework.datastore.generated.model.Event
import com.amplifyframework.datastore.generated.model.EventStatus
import com.amplifyframework.datastore.generated.model.Group
import com.amplifyframework.datastore.generated.model.Status
import com.example.palfinder.R
import com.example.palfinder.backend.services.event.EventAPI
import com.example.palfiner.backend.services.event.EventManager
import kotlinx.android.synthetic.main.fragment_event_create.*
import kotlinx.android.synthetic.main.fragment_event_create.event_description
import kotlinx.android.synthetic.main.fragment_event_create.event_name
import kotlinx.android.synthetic.main.fragment_event_create.view.*
import java.util.*

class EventEditFragment : Fragment() {

    private val TAG = "Event Edit Fragment "

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_event_create, container, false)

        view.btnCreate.setOnClickListener{
            toastExito()
            try {
                  val event = validateForm()
                EventAPI.createEvent(event)
                activity?.finish()
            } catch (e: IllegalStateException){
                Log.e(TAG, "Error in Form")
            }
        }

        view.btnCancel.setOnClickListener{
            toastFallo()
            activity?.finish()
        }

        return view
    }

        private fun validateForm(): Event {

            val name = event_name.text.toString()
            val description = event_description.text.toString()
            val location = event_location.text.toString()
            val date1 = DateUtils.formatISO8601Date(Date())


            return Event.builder()
                .name(name)
                .`when`(Temporal.DateTime(date1))
                .status(EventStatus.OPEN)
                .description(description)
                .image("Nulo")
                .group(Group.builder().name("").description("").image("").status(Status.PUBLIC).id(UUID.randomUUID().toString()).build())
                .location(location)
                .id(UUID.randomUUID().toString())
                .build()
        /*EventManager.EventModel(
                UUID.randomUUID().toString(),
                name,
                description,
                location,
                null,
                null,
                null,
                EventStatus.OPEN,
            )*/




    }

    private fun goTo(tmpView: View, tmpIdElement: Int) {
        Navigation.findNavController(tmpView).navigate(tmpIdElement)
    }

    private fun toastExito(){
        Toast.makeText(context, "Exito", Toast.LENGTH_SHORT).show()
    }

    private fun toastFallo(){
        Toast.makeText(context, "Cancelar", Toast.LENGTH_SHORT).show()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
}