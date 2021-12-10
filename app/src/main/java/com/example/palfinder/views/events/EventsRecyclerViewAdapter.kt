package com.example.palfinder.views.events

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.palfinder.R
import com.example.palfiner.backend.services.event.EventManager
import kotlinx.android.synthetic.main.event_item.view.*


class EventsRecyclerViewAdapter(


    private val values: MutableList<EventManager.EventModel>) :  RecyclerView.Adapter<EventsRecyclerViewAdapter.ViewHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.event_item, parent, false)

       /* view.btnEventDetail.setOnClickListener{

            val instance = EventsListener::class.objectInstance
            instance.onEventsSelected(item, view, R.id.action_eventListManager_to_eventDetail)
            goTo(view, R.id.action_eventListManager_to_eventDetail)
        }*/


        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values?.get(position)

        val eventName = item?.name
        val eventDescription = item?.description
        val eventImage = item?.image
        val objeto = item
        holder.nameView.text = eventName
        holder.descriptionView.text = eventDescription
        /*if (eventImage != null) {
            holder.imageView.setImageBitmap(item.image)
        }*/
        holder.imageView.setOnClickListener {
            it.context.startActivity(Intent(it.context, EventActivity::class.java))
        }

        holder.btnEventDetail.setOnClickListener{

                Log.i("ObjetoFC ",objeto.toString())
            val bundle = bundleOf("id" to item.id)
            goTo(it, R.id.action_eventListManager_to_eventDetail, bundle)
            }
        }




    override fun getItemCount() = values?.size ?: 0

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imageView: ImageView = view.findViewById(R.id.eventImage)
        val nameView: TextView = view.findViewById(R.id.eventName)
        val descriptionView: TextView = view.findViewById(R.id.eventDescription)
        val btnEventDetail: Button = view.findViewById(R.id.btnEventDetail)
        lateinit var eventId: String
    }

    companion object {
        private const val TAG = "Events Adapter"
    }

    private fun goTo(tmpView: View, tmpIdElement: Int, bundle: Bundle) {
        Navigation.findNavController(tmpView).navigate(tmpIdElement, bundle)
    }

    interface EventsAdapterListener {
        

        fun onEventsSelected(event: Any, tmpView: View, tmpIdElement: Int)
    }
}

