package com.example.palfinder.backend.services.event

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.amplifyframework.datastore.generated.model.Event
import com.amplifyframework.datastore.generated.model.User
import com.example.palfiner.backend.services.event.EventManager

// a singleton to hold user data (this is a ViewModel pattern, without inheriting from ViewModel)
object EventData {

    private const val TAG = "EventData"


    private val _currentEvent = MutableLiveData<Event>()
    var currentEvent: LiveData<Event> = _currentEvent



    fun setCurrentEvent(event : Event) {
        // use postvalue() to make the assignation on the main (UI) thread
        _currentEvent.postValue(event)
    }



}