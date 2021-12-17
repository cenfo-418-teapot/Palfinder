package com.example.palfinder.backend.services.event

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.amplifyframework.datastore.generated.model.Event
import com.amplifyframework.datastore.generated.model.EventMembers
import com.amplifyframework.datastore.generated.model.User
import com.example.palfiner.backend.services.event.EventManager

// a singleton to hold user data (this is a ViewModel pattern, without inheriting from ViewModel)
object EventData {

    private const val TAG = "EventData"


    private val _currentEvent = MutableLiveData<EventManager.EventModel>()
    var currentEvent: LiveData<EventManager.EventModel> = _currentEvent

    private val _eventMember = MutableLiveData<EventMembers>()
    var eventMember: LiveData<EventMembers> = _eventMember



    fun setCurrentEvent(event : EventManager.EventModel) {
        // use postvalue() to make the assignation on the main (UI) thread
        _currentEvent.postValue(event)
    }

    fun setEventMembers(eventMembers : EventMembers) {
        // use postvalue() to make the assignation on the main (UI) thread
        _eventMember.postValue(eventMembers)
    }



}