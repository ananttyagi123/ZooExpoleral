package com.example.zooexpoleral

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class MyFirebaseMessagingService : FirebaseMessagingService() {

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        Log.d("FCM", "Notification received: ${remoteMessage.data}")

        if (remoteMessage.data.isNotEmpty()) {
            val title = remoteMessage.data["title"] ?: "New Notification"
            val message = remoteMessage.data["message"] ?: "You have a new message"

            Log.d("FCM", "Title: $title, Message: $message")

            saveNotificationToFirestore(title, message)

            showNotification(title, message)
        } else {
            Log.e("FCM", "Empty notification data received")
        }
    }

    private fun saveNotificationToFirestore(title: String, message: String) {
        val user = FirebaseAuth.getInstance().currentUser
        if (user == null) {
            Log.e("FCM", "User not authenticated")
            return
        }

        val db = FirebaseFirestore.getInstance()
        val notification = hashMapOf(
            "title" to title,
            "message" to message,
            "timestamp" to System.currentTimeMillis()
        )

        db.collection("users").document(user.uid)
            .collection("notifications")
            .add(notification)
            .addOnSuccessListener {
                Log.d("FCM", "Notification saved to Firestore")
            }
            .addOnFailureListener { e ->
                Log.e("FCM", "Error saving notification: ${e.message}")
            }
    }



    private fun showNotification(title: String, message: String) {
        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val channelId = "zoo_explorer_notifications"

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId, "ZooExplorer Notifications",
                NotificationManager.IMPORTANCE_HIGH
            )
            notificationManager.createNotificationChannel(channel)
        }

        val intent = Intent(this, NotificationsActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
        val pendingIntent = PendingIntent.getActivity(
            this, 0, intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val notification = NotificationCompat.Builder(this, channelId)
            .setSmallIcon(R.drawable.baseline_circle_notifications_24)
            .setContentTitle(title)
            .setContentText(message)
            .setAutoCancel(true)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentIntent(pendingIntent)
            .build()

        notificationManager.notify(0, notification)
    }
}