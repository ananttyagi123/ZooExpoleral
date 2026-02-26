package com.example.zooexpoleral

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore
import java.net.HttpURLConnection
import java.net.URL

class FeedbackActivity : AppCompatActivity() {

    private lateinit var nameEditText: EditText
    private lateinit var emailEditText: EditText
    private lateinit var messageEditText: EditText
    private lateinit var ratingBar: RatingBar
    private lateinit var submitButton: Button

    private val firestore = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_feedback)

        nameEditText = findViewById(R.id.nameEditText)
        emailEditText = findViewById(R.id.emailEditText)
        messageEditText = findViewById(R.id.messageEditText)
        ratingBar = findViewById(R.id.ratingBar)
        submitButton = findViewById(R.id.submitButton)

        submitButton.setOnClickListener {
            submitFeedback()
        }
    }

    private fun submitFeedback() {
        val name = nameEditText.text.toString().trim()
        val email = emailEditText.text.toString().trim()
        val message = messageEditText.text.toString().trim()
        val rating = ratingBar.rating

        if (name.isEmpty() || email.isEmpty() || message.isEmpty()) {
            Toast.makeText(this, "Please fill in all fields.", Toast.LENGTH_SHORT).show()
            return
        }

        val url = "https://script.google.com/macros/s/AKfycbx7JgLuuyUHOCTNbGcU5Zz3gmTwJms_JYRXZScHHHx7zwf1rSd58cAtMBeGAuie3K86gw/exec"


        val postData = "name=$name&email=$email&message=$message&rating=$rating"

        Thread {
            try {
                val connection = URL(url).openConnection() as HttpURLConnection
                connection.requestMethod = "POST"
                connection.doOutput = true
                connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded")

                val outputStream = connection.outputStream
                outputStream.write(postData.toByteArray())
                outputStream.flush()
                outputStream.close()

                val responseCode = connection.responseCode
                runOnUiThread {
                    if (responseCode == HttpURLConnection.HTTP_OK) {
                        Toast.makeText(this, "Thanks for your feedback!", Toast.LENGTH_LONG).show()
                        clearForm()
                    } else {
                        Toast.makeText(this, "Failed to submit. Try again.", Toast.LENGTH_LONG).show()
                    }
                }

            } catch (e: Exception) {
                runOnUiThread {
                    Toast.makeText(this, "Error: ${e.message}", Toast.LENGTH_LONG).show()
                }
            }
        }.start()
    }


    private fun clearForm() {
        nameEditText.text.clear()
        emailEditText.text.clear()
        messageEditText.text.clear()
        ratingBar.rating = 0f
    }
}
