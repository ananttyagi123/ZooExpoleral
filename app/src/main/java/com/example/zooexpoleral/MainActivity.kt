package com.example.zooexpoleral

import android.content.Intent
import android.os.Bundle
import android.text.Html
import android.text.InputType
import android.util.Log
import android.view.animation.AlphaAnimation
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var togglePassword: ImageView
    private var isPasswordVisible = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        try {
            if (FirebaseApp.getApps(this).isEmpty()) {
                FirebaseApp.initializeApp(this)
            }
            auth = FirebaseAuth.getInstance()
            Log.d("FirebaseInit", "Firebase initialized successfully")

            if (auth.currentUser != null) {
                startActivity(Intent(this, HomeActivity::class.java))
                finish()
                return
            }
        } catch (e: Exception) {
            Log.e("FirebaseInit", "Error initializing Firebase", e)
            Toast.makeText(this, "Error initializing app. Please try again.", Toast.LENGTH_LONG).show()
            finish()
            return
        }

        setContentView(R.layout.activity_main)

        val rootLayout = findViewById<RelativeLayout>(R.id.rootLayout)
        val emailEditText = findViewById<EditText>(R.id.emailEditText)
        val passwordEditText = findViewById<EditText>(R.id.passwordEditText)
        val loginButton = findViewById<Button>(R.id.loginButton)
        val registerText = findViewById<TextView>(R.id.registerText)
        togglePassword = findViewById(R.id.togglePassword)

        val fadeIn = AlphaAnimation(0f, 1f).apply {
            duration = 800
            fillAfter = true
        }
        rootLayout.startAnimation(fadeIn)

        togglePassword.setOnClickListener {
            isPasswordVisible = !isPasswordVisible
            if (isPasswordVisible) {
                passwordEditText.inputType = InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
                togglePassword.setImageResource(R.drawable.baseline_visibility_24)
            } else {
                passwordEditText.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
                togglePassword.setImageResource(R.drawable.baseline_visibility_off_24)
            }
            passwordEditText.setSelection(passwordEditText.text.length)
        }

        loginButton.setOnClickListener {
            val email = emailEditText.text.toString().trim()
            val password = passwordEditText.text.toString().trim()

            if (email.isNotEmpty() && password.isNotEmpty()) {
                auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Toast.makeText(this, "Login Successful!", Toast.LENGTH_SHORT).show()
                            startActivity(Intent(this, HomeActivity::class.java))
                            finish()
                        } else {
                            Toast.makeText(this, "Login Failed: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                        }
                    }
            } else {
                Toast.makeText(this, "Enter Email & Password", Toast.LENGTH_SHORT).show()
            }
        }

        registerText.text = Html.fromHtml("Don't have an account? <u><b>Register</b></u>")
        registerText.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }
}
