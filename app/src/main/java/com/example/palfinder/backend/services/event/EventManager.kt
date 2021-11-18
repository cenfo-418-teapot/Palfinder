package com.example.palfiner.backend.services.event

import android.graphics.Bitmap
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.amplifyframework.core.model.temporal.Temporal
import com.amplifyframework.datastore.generated.model.*

object EventManager {
    private const val TAG = "EventManager"


    private val _events = MutableLiveData<MutableList<EventManager.EventModel>>(mutableListOf())

    private fun <T> MutableLiveData<T>.notifyObserver() {
        this.postValue(this.value)
    }
    fun notifyObserver() {
        _events.notifyObserver()
    }


    data class EventModel(
        val id: String,
        val name: String,
        val description: String,
        val location: String,
        val date: Temporal.DateTime?,
        val group: Group?,
        val members: List<EventMembers>?,
        val status: Status,
        var imageName: String? = null)
    {
        override fun toString(): String = name

        // bitmap image
        var image : Bitmap? = null
        // return an API GroupData from this Event object
        val data : Event
            get() = Event.builder()
                .name(this.name)
                .`when`(this.date)
                .description(this.description)
                .image(this.imageName)
                .status(this.status)
                .group(this.group)
                .location(this.location)
                .id(this.id)
                .build()

        // static function to create a Event from a Event API object
        companion object {
            fun from(eventData : Event) : EventModel {
                val result = EventModel(
                    eventData.id,
                    eventData.name,
                    eventData.description,
                    eventData.location,
                    eventData.`when`,
                    eventData.group,
                    eventData.members,
                    eventData.status,
                    eventData.image)

                return result
            }
        }

        fun events() : LiveData<MutableList<EventModel>> = _events
        fun addEvent(n : EventModel) {
            val tmpEvents = _events.value
            if (tmpEvents != null) {
                tmpEvents.add(n)
                _events.notifyObserver()
            } else {
                Log.e(TAG, "addGroup : Event collection is null !!")
            }
        }
    }
}