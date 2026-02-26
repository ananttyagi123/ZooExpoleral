package com.example.zooexpoleral

import android.widget.Button
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore


class NotificationsActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: NotificationsAdapter
    private val notificationsList = mutableListOf<NotificationItem>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notifications)

        recyclerView = findViewById(R.id.recyclerViewNotifications)
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = NotificationsAdapter(notificationsList)
        recyclerView.adapter = adapter

        fetchNotifications()
    }



    private fun fetchNotifications() {
        val user = FirebaseAuth.getInstance().currentUser
        if (user != null) {
            val db = FirebaseFirestore.getInstance()
            val twoDaysAgo = System.currentTimeMillis() - 2 * 24 * 60 * 60 * 1000

            db.collection("users").document(user.uid)
                .collection("notifications")
                .whereGreaterThan("timestamp", twoDaysAgo)
                .orderBy("timestamp")
                .get()
                .addOnSuccessListener { documents ->
                    notificationsList.clear()
                    for (doc in documents) {
                        val title = doc.getString("title") ?: "No Title"
                        val message = doc.getString("message") ?: "No Message"
                        val read = doc.getBoolean("read") ?: false
                        val id = doc.id

                        notificationsList.add(NotificationItem(title, message, id, read))

                        // ✅ Mark as read if it was unread
                        if (!read) {
                            db.collection("users").document(user.uid)
                                .collection("notifications").document(id)
                                .update("read", true)
                        }
                    }
                    adapter.notifyDataSetChanged()
                }
                .addOnFailureListener { e ->
                    Log.e("Firestore", "Error fetching notifications", e)
                }
        }
    }

}
