package com.example.zooexpoleral

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatDelegate
import java.util.*

class App : Application() {

    private lateinit var sharedPrefs: SharedPreferences

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(LocaleHelper.setLocale(base, LocaleHelper.getLanguage(base)))
    }

    override fun onCreate() {
        super.onCreate()

        sharedPrefs = getSharedPreferences("AppSettings", MODE_PRIVATE)

        applyTheme()

        applyLanguage()
    }

    private fun applyTheme() {
        val isDarkMode = sharedPrefs.getBoolean("dark_mode", false)
        AppCompatDelegate.setDefaultNightMode(
            if (isDarkMode) AppCompatDelegate.MODE_NIGHT_YES else AppCompatDelegate.MODE_NIGHT_NO
        )
    }

    private fun applyLanguage() {
        val languageCode = sharedPrefs.getString("language_code", "en") ?: "en"
        val locale = Locale(languageCode)
        Locale.setDefault(locale)

        val config = resources.configuration
        config.setLocale(locale)
        resources.updateConfiguration(config, resources.displayMetrics)
    }
}
