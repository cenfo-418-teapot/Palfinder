package com.example.palfinder.views.events

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.palfinder.R
import com.example.palfiner.backend.services.event.EventManager

class EventsAdapter(
    private val values: MutableList<EventManager.EventModel>
) :
    RecyclerView.Adapter<EventsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.event_item, parent, false)

        view.btnDetail.setOnClickListener{
            goTo(view, R.id.action_groupEditFragment_to_groupListFragment)
        }
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values?.get(position)

        val eventName = item?.name
        val eventDescription = item?.description
        val eventImage = item?.image
        holder.nameView.text = eventName
        holder.descriptionView.text = eventDescription
        /*if (eventImage != null) {
            holder.imageView.setImageBitmap(item.image)
        }*/
        holder.imageView.setOnClickListener {
            it.context.startActivity(Intent(it.context, EventActivity::class.java))
        }

    }

    override fun getItemCount() = values?.size ?: 0

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imageView: ImageView = view.findViewById(R.id.eventImage)
        val nameView: TextView = view.findViewById(R.id.eventName)
        val descriptionView: TextView = view.findViewById(R.id.eventDescription)
        val btnDetail: Button = view.findViewById(R.id.btnEventDetail)
        lateinit var eventId: String
    }

    companion object {
        private const val TAG = "Events Adapter"
    }

    private fun goTo(tmpView: View, tmpIdElement: Int) {
        Navigation.findNavController(tmpView).navigate(tmpIdElement)
    }
}


        //Match ids línea 53
        //ONbindViewHolder setListeners que necesito y datos para rellenear el event item
// on created view se infla el xml correcto