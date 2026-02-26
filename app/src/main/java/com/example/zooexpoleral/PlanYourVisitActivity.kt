package com.example.zooexpoleral

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import java.util.*

class PlanYourVisitActivity : AppCompatActivity() {

    private lateinit var btnSelectDate: Button
    private lateinit var btnSelectTime: Button
    private lateinit var tvSelectedDate: TextView
    private lateinit var tvSelectedTime: TextView
    private lateinit var btnGetDirections: Button
    private lateinit var btnBookTickets: Button

    private val calendar = Calendar.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_plan_your_visit)

        btnSelectDate = findViewById(R.id.btnSelectDate)
        btnSelectTime = findViewById(R.id.btnSelectTime)
        tvSelectedDate = findViewById(R.id.tvSelectedDate)
        tvSelectedTime = findViewById(R.id.tvSelectedTime)
        btnGetDirections = findViewById(R.id.btnGetDirections)
        btnBookTickets = findViewById(R.id.btnBookTickets)

        btnSelectDate.setOnClickListener {
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)

            val datePickerDialog = DatePickerDialog(this, { _, selectedYear, selectedMonth, selectedDay ->
                val formattedDate = "$selectedDay/${selectedMonth + 1}/$selectedYear"
                tvSelectedDate.text = formattedDate
            }, year, month, day)

            datePickerDialog.show()
        }

        btnSelectTime.setOnClickListener {
            val hour = calendar.get(Calendar.HOUR_OF_DAY)
            val minute = calendar.get(Calendar.MINUTE)

            val timePickerDialog = TimePickerDialog(this, { _, selectedHour, selectedMinute ->
                val formattedTime = String.format("%02d:%02d", selectedHour, selectedMinute)
                tvSelectedTime.text = formattedTime
            }, hour, minute, true)

            timePickerDialog.show()
        }

        btnGetDirections.setOnClickListener {
            val zooLocationUri = Uri.parse("geo:0,0?q=Your+Zoo+Name+Location")
            val mapIntent = Intent(Intent.ACTION_VIEW, zooLocationUri)
            mapIntent.setPackage("com.google.android.apps.maps")
            startActivity(mapIntent)
        }

        btnBookTickets.setOnClickListener {
            Toast.makeText(this, "Redirecting to ticket booking...", Toast.LENGTH_SHORT).show()

        }
    }
}
