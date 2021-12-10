package com.example.palfinder.views.events

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.navGraphViewModels
import com.amplifyframework.datastore.generated.model.Event
import com.example.palfinder.R
import com.example.palfinder.backend.services.event.EventAPI
import com.example.palfiner.backend.services.event.EventManager
import kotlinx.android.synthetic.main.fragment_event_create.view.*

private const val ARG_ID = "id"

class EventDetail : Fragment(){

    private var  id :String? = null

    var event : Any? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
             id = it.getString(ARG_ID)
            Log.i("Prueba", id.toString())

            event = EventAPI.getEventById(id.toString())
            Log.i("Prueba", event.toString())
        }


    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_event_detail, container, false)


        return view
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


