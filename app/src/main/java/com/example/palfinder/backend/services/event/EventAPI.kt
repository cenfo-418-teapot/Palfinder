package com.example.palfinder.backend.services.event

import android.util.Log
import com.amplifyframework.api.graphql.model.ModelMutation
import com.amplifyframework.core.Amplify
import com.amplifyframework.datastore.generated.model.Event
import com.example.palfiner.backend.services.event.EventManager

object EventAPI {

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
}