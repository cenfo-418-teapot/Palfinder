package com.example.palfiner.backend.services.event

import android.graphics.Bitmap
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.amplifyframework.core.model.temporal.Temporal
import com.amplifyframework.datastore.generated.model.*
import com.example.palfinder.backend.services.GroupAdmin

object EventManager {

    private const val TAG = "EventManager"

    private val _events = MutableLiveData<MutableList<EventManager.EventModel>>(mutableListOf())

    private val _members = MutableLiveData<MutableList<EventManager.EventModel>>(mutableListOf())

    private fun <T> MutableLiveData<T>.notifyObserver() {
        this.postValue(this.value)
    }
    fun notifyObserver() {
        _events.notifyObserver()
    }


    fun events() : LiveData<MutableList<EventModel>> = _events
    fun addEvent(n : EventModel) {
        val tmpEvents = _events.value
        if (tmpEvents != null) {
            tmpEvents.add(n)
            _events.notifyObserver()
        } else {
            Log.e(EventManager.TAG, "addGroup : group collection is null !!")
        }
    }
    fun deleteEvent(at: Int) : EventModel?  {
        val event = _events.value?.removeAt(at)
        _events.notifyObserver()
        return event
    }

    fun getEvent(at: Int): EventModel? {
        return _events.value?.get(at)
    }

    fun resetEvents() {
        this._events.value?.clear()  //used when signing out
        _events.notifyObserver()
    }

    data class EventModel(
        val id: String,
        val name: String,
        val description: String,
        val location: String?,
        val date: Temporal.DateTime?,
        val group: Group?,
        val members: List<EventMembers>?,
        val status: EventStatus?,
        var imageName: String? = null)
    {
        override fun toString(): String = name

        // bitmap image
        var image : Bitmap? = null
        // return an API EventData from this Event object
        val data : Event
            get() = Event.builder()
                .name(this.name)
                .`when`(this.date)
                .status(this.status)
                .description(this.description)
                .image(this.imageName)
                .group(this.group)
                .location(this.location)
                .id(this.id)
                .build()

        // static function to create a Event from a Event API object
        companion object {
            fun from(eventData: Event): EventModel {

                return EventModel(
                    eventData.id,
                    eventData.name,
                    eventData.description,
                    eventData.location,
                    eventData.`when`,
                    eventData.group,
                    eventData.members,
                    eventData.status,
                    eventData.image
                )
            }
        }

        fun events() : LiveData<MutableList<EventModel>> = _events
        fun addEvent(n : EventModel) {
            val tmpEvents = _events.value
            if (tmpEvents != null) {
                tmpEvents.add(n)
                _events.notifyObserver()
            } else {
                Log.e(TAG, "addEvent : Event collection is null !!")
            }
        }
    }
}
