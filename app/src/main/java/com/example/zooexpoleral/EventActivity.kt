package com.example.zooexpoleral

import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings
import org.json.JSONArray

class EventActivity : AppCompatActivity() {

    private lateinit var adapter: EventAdapter
    private lateinit var remoteConfig: FirebaseRemoteConfig

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event)

        val eventRecyclerView = findViewById<RecyclerView>(R.id.eventRecyclerView)
        eventRecyclerView.layoutManager = LinearLayoutManager(this)

        adapter = EventAdapter(this, mutableListOf())
        eventRecyclerView.adapter = adapter

        remoteConfig = FirebaseRemoteConfig.getInstance()

        val configSettings = FirebaseRemoteConfigSettings.Builder()
            .setMinimumFetchIntervalInSeconds(3600) // Fetch every hour
            .build()
        remoteConfig.setConfigSettingsAsync(configSettings)

        remoteConfig.setDefaultsAsync(R.xml.remote_config_defaults)

        fetchEventData()

        val backButton = findViewById<Button>(R.id.backButton)
        backButton.setOnClickListener {
            finish()
        }
    }

    private fun fetchEventData() {
        remoteConfig.fetchAndActivate()
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Log.d("RemoteConfig", "Fetch successful")
                    val jsonString = remoteConfig.getString("event_list")
                    Log.d("RemoteConfig", "Fetched event_list: $jsonString")
                    val eventsList = mutableListOf<EventItem>()
                    try {
                        val jsonArray = JSONArray(jsonString)
                        for (i in 0 until jsonArray.length()) {
                            val obj = jsonArray.getJSONObject(i)
                            eventsList.add(
                                EventItem(
                                    obj.getString("title"),
                                    obj.getString("time"),
                                    obj.getString("description")
                                )
                            )
                        }
                        adapter.updateEvents(eventsList)
                    } catch (e: Exception) {
                        Log.e("RemoteConfig", "JSON Parsing Error: ${e.message}")
                    }
                } else {
                    Log.e("RemoteConfig", "Fetch failed")
                }
            }
    }

}
