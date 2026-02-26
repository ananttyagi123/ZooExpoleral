package com.example.zooexpoleral

import android.view.Gravity
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import android.view.View
import android.widget.TextView

class ChatAdapter(private val messages: List<Message>, private val currentUserId: String) :
    RecyclerView.Adapter<ChatAdapter.ChatViewHolder>() {

    class ChatViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val messageText: TextView = itemView.findViewById(android.R.id.text1)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatViewHolder {
        val textView = TextView(parent.context)
        textView.setPadding(16, 8, 16, 8)
        return ChatViewHolder(textView)
    }

    override fun onBindViewHolder(holder: ChatViewHolder, position: Int) {
        val msg = messages[position]
        holder.messageText.text = msg.text
        holder.messageText.gravity = if (msg.senderId == currentUserId) Gravity.END else Gravity.START
    }

    override fun getItemCount(): Int = messages.size
}
