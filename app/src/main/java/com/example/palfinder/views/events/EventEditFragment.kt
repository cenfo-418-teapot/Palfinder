package com.example.palfinder.views.events

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.navigation.Navigation
import com.amazonaws.util.DateUtils
import com.amplifyframework.auth.AuthUserAttributeKey.name
import com.amplifyframework.core.model.temporal.Temporal
import com.amplifyframework.datastore.generated.model.*
import com.example.palfinder.R
import com.example.palfinder.backend.services.UserData
import com.example.palfinder.backend.services.event.EventAPI
import com.example.palfinder.backend.services.event.EventAPI.updateEvent
import com.example.palfiner.backend.services.event.EventManager
import kotlinx.android.synthetic.main.fragment_event_create.*
import kotlinx.android.synthetic.main.fragment_event_create.event_description
import kotlinx.android.synthetic.main.fragment_event_create.event_name
import kotlinx.android.synthetic.main.fragment_event_create.view.*
import java.util.*

class EventEditFragment : Fragment() {

    private val TAG = "Event Edit Fragment "
    private var currentUser = UserData.currentUser.value!!
    private var _eventMembers = MutableLiveData<EventMembers>()
    var eventMembers: LiveData<EventMembers> = _eventMembers

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

                var event = validateForm()
                EventAPI.createEvent(event)

               EventAPI.createEventMember(EventMembers.builder()
                   .role(EventRoles.ADMIN)
                   .user(currentUser)
                   .event(event)
                   .id(UUID.randomUUID().toString())
                   .build())
                Navigation.findNavController(view).navigate(R.id.action_creteEvent_to_groupProfile)

            } catch (e: IllegalStateException){
                Log.e(TAG, "Error in Form")
            }
        }

        view.btnCancel.setOnClickListener{
            toastFallo()
            Navigation.findNavController(view).navigate(R.id.action_creteEvent_to_groupProfile)
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
