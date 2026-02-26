package com.example.zooexpoleral


import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class ResultActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        val tvScore = findViewById<TextView>(R.id.tvScore)
        val tvFeedback = findViewById<TextView>(R.id.tvFeedback)
        val btnRestart = findViewById<Button>(R.id.btnRestart)
        val btnHome = findViewById<Button>(R.id.btnHome)


        val score = intent.getIntExtra("USER_SCORE", 0)
        val total = intent.getIntExtra("TOTAL_QUESTIONS", 0)

        tvScore.text = "Your Score: $score / $total"

        tvFeedback.text = when {
            score == total -> "Amazing! You're an animal expert 🦁"
            score > total * 0.7 -> "Great job! You know your animals 🐯"
            score > total * 0.4 -> "Not bad! Keep learning 🐢"
            else -> "Try again! You can do better 🐛"
        }

        btnRestart.setOnClickListener {
            val intent = Intent(this, GuessAnimalActivity::class.java)
            startActivity(intent)
            finish()
        }

        btnHome.setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}
