package com.example.zooexpoleral

import android.app.AlertDialog
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.text.InputType
import android.widget.*
import androidx.appcompat.app.AppCompatDelegate
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth

class Setting : BaseActivity() {

    private lateinit var switchNotifications: Switch
    private lateinit var switchDarkMode: Switch
    private lateinit var layoutLanguage: LinearLayout
    private lateinit var layoutDeleteAccount: LinearLayout

    private lateinit var sharedPrefs: SharedPreferences
    private val auth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)

        sharedPrefs = getSharedPreferences("AppSettings", MODE_PRIVATE)

        switchNotifications = findViewById(R.id.switchNotifications)
        switchDarkMode = findViewById(R.id.switchDarkMode)
        layoutLanguage = findViewById(R.id.layoutLanguage)
        layoutDeleteAccount = findViewById(R.id.layoutDeleteAccount)

        switchNotifications.isChecked = sharedPrefs.getBoolean("notifications_enabled", true)
        switchDarkMode.isChecked = sharedPrefs.getBoolean("dark_mode", false)

        switchNotifications.setOnCheckedChangeListener { _, isChecked ->
            sharedPrefs.edit().putBoolean("notifications_enabled", isChecked).apply()
            Toast.makeText(this, "Notifications ${if (isChecked) "enabled" else "disabled"}", Toast.LENGTH_SHORT).show()
        }

        switchDarkMode.setOnCheckedChangeListener { _, isChecked ->
            sharedPrefs.edit().putBoolean("dark_mode", isChecked).apply()

            AppCompatDelegate.setDefaultNightMode(
                if (isChecked) AppCompatDelegate.MODE_NIGHT_YES else AppCompatDelegate.MODE_NIGHT_NO
            )

            restartApplication()
        }

        layoutLanguage.setOnClickListener {
            showLanguageDialog()
        }

        layoutDeleteAccount.setOnClickListener {
            showReAuthDialog()
        }
    }

    private fun showLanguageDialog() {
        val languages = arrayOf("English", "हिन्दी")
        val codes = arrayOf("en", "hi")

        AlertDialog.Builder(this)
            .setTitle(getString(R.string.select_language))
            .setItems(languages) { _, which ->
                val languageCode = codes[which]

                sharedPrefs.edit().putString("language", languageCode).apply()

                LocaleHelper.applySavedLocale(this)
                recreate()

            }
            .show()
    }


    private fun showReAuthDialog() {
        val email = auth.currentUser?.email ?: return

        val passwordInput = EditText(this).apply {
            hint = "Enter your password"
            inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
        }

        AlertDialog.Builder(this)
            .setTitle("Re-authenticate")
            .setMessage("To delete your account, please re-enter your password.")
            .setView(passwordInput)
            .setPositiveButton("Delete") { _, _ ->
                val password = passwordInput.text.toString()
                val credential = EmailAuthProvider.getCredential(email, password)
                auth.currentUser?.reauthenticate(credential)?.addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        auth.currentUser?.delete()?.addOnCompleteListener { delTask ->
                            if (delTask.isSuccessful) {
                                Toast.makeText(this, "Account deleted.", Toast.LENGTH_SHORT).show()
                                val intent = Intent(this, MainActivity::class.java)
                                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                                startActivity(intent)
                            } else {
                                Toast.makeText(this, "Delete failed. Try again.", Toast.LENGTH_SHORT).show()
                            }
                        }
                    } else {
                        Toast.makeText(this, "Re-authentication failed.", Toast.LENGTH_LONG).show()
                    }
                }
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    fun restartApplication() {
        val intent = Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        startActivity(intent)
        finishAffinity()
    }

}
