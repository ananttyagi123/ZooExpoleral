package com.example.zooexpoleral

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.animation.*
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.airbnb.lottie.LottieAnimationView

class SplashActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val logo = findViewById<ImageView>(R.id.logoImage)
        val loadingText = findViewById<TextView>(R.id.loadingText)
        val lottieBackground = findViewById<LottieAnimationView>(R.id.lottieBackground)

        lottieBackground.playAnimation()

        val fadeIn = AlphaAnimation(0f, 1f).apply {
            duration = 1000
            fillAfter = true
        }

        val rotate = RotateAnimation(
            0f, 360f,
            Animation.RELATIVE_TO_SELF, 0.5f,
            Animation.RELATIVE_TO_SELF, 0.5f
        ).apply {
            duration = 1000
            interpolator = LinearInterpolator()
        }

        val animationSet = AnimationSet(true).apply {
            addAnimation(fadeIn)
            addAnimation(rotate)
        }

        logo.startAnimation(animationSet)

        val blinkAnimation = AlphaAnimation(0.0f, 1.0f).apply {
            duration = 800
            startOffset = 20
            repeatMode = AlphaAnimation.REVERSE
            repeatCount = AlphaAnimation.INFINITE
        }
        loadingText.startAnimation(blinkAnimation)

        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }, 3000)
    }
}
