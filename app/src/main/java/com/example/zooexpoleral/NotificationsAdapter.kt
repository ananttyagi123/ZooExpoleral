package com.example.zooexpoleral

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

data class NotificationItem(val title: String, val message: String , val id: String = "", val read: Boolean = false)

class NotificationsAdapter(private val notifications: List<NotificationItem>) :
    RecyclerView.Adapter<NotificationsAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val title: TextView = view.findViewById(R.id.notificationTitle)
        val message: TextView = view.findViewById(R.id.notificationMessage)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_notification, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val notification = notifications[position]
        holder.title.text = notification.title
        holder.message.text = notification.message

        if (!notification.read) {
            holder.title.setTextColor(holder.itemView.context.getColor(R.color.red))
            holder.title.setTypeface(null, android.graphics.Typeface.BOLD)
        } else {
            holder.title.setTextColor(holder.itemView.context.getColor(R.color.black))
            holder.title.setTypeface(null, android.graphics.Typeface.NORMAL)
        }
    }


    override fun getItemCount() = notifications.size
}
