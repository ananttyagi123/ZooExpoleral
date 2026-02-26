package com.example.zooexpoleral

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class AboutActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about)
    }

    fun openPrivacyPolicy(view: android.view.View) {
        val url = "https://www.zooexplorer.com/privacy-policy"
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse(url)

        if (intent.resolveActivity(packageManager) != null) {
            startActivity(intent)
        } else {
            Toast.makeText(this, "No browser found to open the Privacy Policy.", Toast.LENGTH_SHORT).show()
        }
    }
}
