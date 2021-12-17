package com.example.palfinder.backend.services.event

import android.util.Log
import com.amazonaws.Response
import com.amplifyframework.api.graphql.GraphQLResponse
import com.amplifyframework.api.graphql.model.ModelMutation
import com.amplifyframework.api.graphql.model.ModelQuery
import com.amplifyframework.core.Amplify
import com.amplifyframework.core.model.query.Where
import com.amplifyframework.datastore.generated.model.Event
import com.amplifyframework.datastore.generated.model.EventMembers
import com.amplifyframework.datastore.generated.model.Group
import com.example.palfinder.backend.services.GroupAdmin
import com.example.palfinder.backend.services.GroupService
import com.example.palfinder.views.events.EventDetail
import com.example.palfiner.backend.services.event.EventManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine


object EventAPI {

    val TAG = "Event manager API"


    fun updateEvents() {
        val eventList = EventManager.events().value
        val isEmpty = eventList?.isEmpty() ?: false

        // query notes when signed in and we do not have Notes yet
        if (isEmpty) {
            this.queryEvents()
        } else {
            EventManager.resetEvents()
            this.queryEvents()
        }
    }


    fun queryEvents() {
        Log.i(TAG, "Querying Events")

        Amplify.API.query(
            ModelQuery.list(Event::class.java),
            { response ->
                Log.i(TAG, "Queried")
                for (eventData in response.data) {
                    Log.i(TAG, eventData.name)
                    // TODO should add all the groups at once instead of one by one (each add triggers a UI refresh)
                    EventManager.addEvent(EventManager.EventModel.from(eventData))
                }
            },
            { error -> Log.e(TAG, "Query failure", error) }
        )
    }

  /*  fun queryEventMembers(eventId: String) {
        Log.i(TAG, "Querying Event Mmeber by Id")

        Amplify.API.query(
            ModelQuery.list(EventMembers::class.java),
            { response ->
                Log.i(TAG, "Queried")
                for (eventData in response.data) {
                    Log.i(TAG, eventData.id)
                    // TODO should add all the groups at once instead of one by one (each add triggers a UI refresh)
                    EventManager.addEvent(EventManager.EventModel.from(eventData))
                }
            },
            { error -> Log.e(TAG, "Query failure", error) }
        )
    }*/

    fun createEvent(event : Event) {

        Amplify.API.mutate(
            ModelMutation.create(event),
            { response ->
                Log.i("Event Mnager ", "Created")
                if (response.hasErrors()) {
                    Log.e("Event Mnager ", response.errors.first().message)
                } else {
                    Log.i("Event Mnager ", "Added  Event with id: " + response.data.id)
                }
            },
            { error ->
                Log.e("Event Mnager ", "Create failed", error)
            }
        )
    }

    fun createEventMember(member : EventMembers) {

        Amplify.API.mutate(
            ModelMutation.create(member),
            { response ->
                Log.i("Event Mnager ", "Created")
                if (response.hasErrors()) {
                    Log.e("Event Mnager ", response.errors.first().message)
                } else {
                    Log.i("Event Mnager ", "Added  Event with id: " + response.data.id)
                }
            },
            { error ->
                Log.e("Event Mnager ", "Create failed", error)
            }
        )
    }


    fun updateEvent(event: Event) : Event? {


        var eventoDB : Event? = null

        Amplify.API.mutate(
            ModelMutation.update(event),
            { response ->
                Log.i("Event Mnager ", "Created")
                if (response.hasErrors()) {
                    Log.e("Event Mnager ", response.errors.first().message)
                } else {
                    Log.i("Event Mnager ", "Updated  Event with id: " + response.data.id)
                }
            },
            { error ->
                Log.e("Event Mnager ", "Update failed", error)
            }
        )

        Amplify.API.query(
            ModelQuery.get(Event::class.java, event.id.toString()),
            { response ->
                Log.i("Event Mnager ", "Created")
                if (response.hasErrors()) {
                    Log.e("Event Mnager ", response.errors.first().message)
                } else {
                    Log.i("Event Mnager ", "Updated  Event with id: " + response.data.id)

                    if (response.data != null) {

                        eventoDB = response.data.copyOfBuilder().build()
                    }

                }
            },
            { error ->
                Log.e("Event Mnager ", "Update failed", error)
            }
            //{ Log.i("MyAmplifyApp", "Query results = ${(it.data as Event).name}")},
            //{ Log.e("MyAmplifyApp", "Query failed", it) }
        )
        return eventoDB
    }

    fun getEventById(eventId : String): Event? {

        var eventoDB : Event? = null

        Amplify.API.query(
            ModelQuery.get(Event::class.java, eventId),
            { response ->
                Log.i("Event Mnager ", "Created")
                if (response.hasErrors()) {
                    Log.e("Event Mnager ", response.errors.first().message)
                } else {
                    Log.i("Event Mnager ", "Updated  Event with id: " + response.data.id)

                    if (response.data != null) {
                        eventoDB = response.data.copyOfBuilder().build()
                    }

                }
            },
            { error ->
                Log.e("Event Mnager ", "Update failed", error)
            }
            //{ Log.i("MyAmplifyApp", "Query results = ${(it.data as Event).name}")},
            //{ Log.e("MyAmplifyApp", "Query failed", it) }
        )
        return eventoDB
    }
}