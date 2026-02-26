package com.example.zooexpoleral

import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AnimalSoundsGameActivity : AppCompatActivity() {

    private lateinit var playRandomSoundButton: Button
    private lateinit var scoreTextView: TextView
    private lateinit var answerButton1: Button
    private lateinit var answerButton2: Button
    private lateinit var answerButton3: Button
    private lateinit var viewLeaderboardButton: Button
    private var score = 0

    private val animals = listOf("Elephant", "Lion", "Tiger", "Giraffe", "Panda", "Zebra", "Cheetah", "Kangaroo", "Polar Bear", "Wolf", "Flamingo", "Penguin", "Crocodile", "Peacock", "Rhinoceros", "Hippopotamus")
    private var currentAnimal: String = animals.random()

    private var mediaPlayer: MediaPlayer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_animal_sounds_game)

        playRandomSoundButton = findViewById(R.id.playRandomSoundButton)
        scoreTextView = findViewById(R.id.scoreTextView)
        answerButton1 = findViewById(R.id.answerButton1)
        answerButton2 = findViewById(R.id.answerButton2)
        answerButton3 = findViewById(R.id.answerButton3)
        viewLeaderboardButton = findViewById(R.id.viewLeaderboardButton)

        answerButton1.setOnClickListener { checkAnswer(answerButton1.text.toString()) }
        answerButton2.setOnClickListener { checkAnswer(answerButton2.text.toString()) }
        answerButton3.setOnClickListener { checkAnswer(answerButton3.text.toString()) }

        playRandomSoundButton.setOnClickListener {
            playAnimalSound(currentAnimal)
        }

        viewLeaderboardButton.setOnClickListener {
            startActivity(Intent(this, LeaderboardActivity::class.java))
        }

        playRandomAnimalSound()
    }

    private fun playAnimalSound(animal: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val soundResId = when (animal) {
                "Elephant" -> R.raw.elephant_sound
                "Lion" -> R.raw.lion_sound
                "Tiger" -> R.raw.tiger_sound
                "Giraffe" -> R.raw.zirraffe_sound
                "Panda" -> R.raw.panda_sound
                "Zebra" -> R.raw.zebra_sound
                "Cheetah" -> R.raw.cheetah_sound
                "Kangaroo" -> R.raw.kangaroo_sound
                "Polar Bear" -> R.raw.polarbear_sound
                "Wolf" -> R.raw.wolf_sound
                "Flamingo" -> R.raw.flamingo_sound
                "Penguin" -> R.raw.pinguin_sound
                "Crocodile" -> R.raw.crocodile
                "Peacock" -> R.raw.peacock_sound
                "Rhinoceros" -> R.raw.rhinoceros_sound
                "Hippopotamus" -> R.raw.hippopotamus_sound
                else -> throw IllegalArgumentException("Unknown animal sound")
            }

            mediaPlayer?.release()
            mediaPlayer = MediaPlayer.create(applicationContext, soundResId)
            mediaPlayer?.start()

            mediaPlayer?.setOnCompletionListener {
                CoroutineScope(Dispatchers.Main).launch {
                    it.release()
                }
            }
        }
    }

    private fun playRandomAnimalSound() {

        var newAnimal = animals.random()

        while (newAnimal == currentAnimal) {
            newAnimal = animals.random()
        }

        currentAnimal = animals.random()

        updateAnswerButtons()

        Toast.makeText(this, "Click 'Play Sound' to hear the animal", Toast.LENGTH_SHORT).show()
    }

    private fun updateAnswerButtons() {
        val wrongAnswers = animals.filter { it != currentAnimal }.shuffled().take(2)

        val answerOptions = (wrongAnswers + currentAnimal).shuffled()

        answerButton1.text = answerOptions[0]
        answerButton2.text = answerOptions[1]
        answerButton3.text = answerOptions[2]
    }

    private fun checkAnswer(selectedAnimal: String) {
        if (selectedAnimal == currentAnimal) {
            score++
            Toast.makeText(this, "Correct!", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Wrong! It was $currentAnimal", Toast.LENGTH_SHORT).show()
        }

        scoreTextView.text = "Score: $score"

        saveScoreToFirestore()

        playRandomAnimalSound()
    }

    private fun saveScoreToFirestore() {
        val user = FirebaseAuth.getInstance().currentUser
        if (user != null) {
            val db = FirebaseFirestore.getInstance()

            val username = user.displayName ?: user.email ?: "Unknown User"

            val scoreData = hashMapOf(
                "username" to username,
                "score" to score,
                "timestamp" to FieldValue.serverTimestamp()
            )

            db.collection("leaderboard")
                .add(scoreData)
                .addOnSuccessListener {
                    Log.d("Leaderboard", "Score saved to Firestore")
                }
                .addOnFailureListener { e ->
                    Log.e("Leaderboard", "Error saving score", e)
                }
        } else {
            Log.e("Leaderboard", "User not authenticated")
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer?.release()
    }
}
