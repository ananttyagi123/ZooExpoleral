package com.example.zooexpoleral

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query

class LeaderboardActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var leaderboardAdapter: LeaderboardAdapter
    private val leaderboardList = mutableListOf<ScoreItem>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_leaderboard)

        recyclerView = findViewById(R.id.recyclerViewLeaderboard)
        recyclerView.layoutManager = LinearLayoutManager(this)
        leaderboardAdapter = LeaderboardAdapter(leaderboardList)
        recyclerView.adapter = leaderboardAdapter

        fetchLeaderboard()
    }

    private fun fetchLeaderboard() {
        val db = FirebaseFirestore.getInstance()
        db.collection("leaderboard")
            .orderBy("score", Query.Direction.DESCENDING)
            .limit(10)
            .get()
            .addOnSuccessListener { documents ->
                leaderboardList.clear()
                for (document in documents) {
                    val username = document.getString("username") ?: "Unknown"
                    val score = document.getLong("score")?.toInt() ?: 0
                    leaderboardList.add(ScoreItem(username, score))
                }
                leaderboardAdapter.notifyDataSetChanged()
            }
            .addOnFailureListener { e ->
                Log.e("Leaderboard", "Error fetching leaderboard", e)
            }
    }
}
