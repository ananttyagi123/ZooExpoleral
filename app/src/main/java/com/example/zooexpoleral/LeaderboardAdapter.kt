package com.example.zooexpoleral

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

import androidx.recyclerview.widget.RecyclerView



class LeaderboardAdapter(private val leaderboard: List<ScoreItem>) :
    RecyclerView.Adapter<LeaderboardAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val username: TextView = view.findViewById(R.id.username)
        val score: TextView = view.findViewById(R.id.score)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_leaderboard, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val scoreItem = leaderboard[position]
        holder.username.text = scoreItem.username ?: "Unknown"
        holder.score.text = "Score: ${scoreItem.score}"
    }

    override fun getItemCount() = leaderboard.size
}
