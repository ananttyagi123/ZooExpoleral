package com.example.zooexpoleral

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

data class EventItem(
    val title: String,
    val time: String,
    val description: String
)

class EventAdapter(
    private val context: Context,
    private var events: MutableList<EventItem>
) : RecyclerView.Adapter<EventAdapter.EventViewHolder>() {

    inner class EventViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val eventTitle: TextView = itemView.findViewById(R.id.eventTitle)
        val eventTime: TextView = itemView.findViewById(R.id.eventTime)
        val eventDescription: TextView = itemView.findViewById(R.id.eventDescription)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_event, parent, false)
        return EventViewHolder(view)
    }

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        val event = events[position]
        holder.eventTitle.text = event.title
        holder.eventTime.text = event.time
        holder.eventDescription.text = event.description
    }

    override fun getItemCount(): Int = events.size

    // Method to update the events list
    fun updateEvents(newEvents: List<EventItem>) {
        events.clear()
        events.addAll(newEvents)
        notifyDataSetChanged()
    }
}
