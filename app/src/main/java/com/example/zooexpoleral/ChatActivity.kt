package com.example.zooexpoleral

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import android.widget.Button
import android.widget.EditText


class ChatActivity : AppCompatActivity() {

    private lateinit var db: FirebaseFirestore
    private lateinit var auth: FirebaseAuth
    private lateinit var adapter: ChatAdapter
    private val messages = mutableListOf<Message>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        db = FirebaseFirestore.getInstance()
        auth = FirebaseAuth.getInstance()

        val chatRecyclerView: RecyclerView = findViewById(R.id.chatRecyclerView)
        val messageEditText: EditText = findViewById(R.id.messageEditText)
        val sendButton: Button = findViewById(R.id.sendButton)

        adapter = ChatAdapter(messages, auth.currentUser?.uid ?: "")
        chatRecyclerView.layoutManager = LinearLayoutManager(this)
        chatRecyclerView.adapter = adapter

        sendButton.setOnClickListener {
            val text = messageEditText.text.toString()
            if (text.isNotEmpty()) {
                val msg = Message(senderId = auth.currentUser!!.uid, text = text)
                db.collection("chats").add(msg)
                messageEditText.text.clear()
            }
        }

        db.collection("chats")
            .orderBy("timestamp", Query.Direction.ASCENDING)
            .addSnapshotListener { snapshots, _ ->
                messages.clear()
                snapshots?.forEach {
                    val msg = it.toObject(Message::class.java)
                    messages.add(msg)
                }
                adapter.notifyDataSetChanged()
                chatRecyclerView.scrollToPosition(messages.size - 1)
            }
    }
}
