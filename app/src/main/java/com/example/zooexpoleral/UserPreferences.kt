package com.example.zooexpoleral
import android.content.Context
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore by preferencesDataStore("user_profile")

class UserPreferences(private val context: Context) {
    companion object {
        val NAME = stringPreferencesKey("name")
        val EMAIL = stringPreferencesKey("email")
        val PHONE = stringPreferencesKey("phone")
        val ADDRESS = stringPreferencesKey("address")
        val GENDER = stringPreferencesKey("gender")
        val DOB = stringPreferencesKey("dob")
    }

    val userData: Flow<Map<String, String>> = context.dataStore.data.map { prefs ->
        mapOf(
            "name" to (prefs[NAME] ?: ""),
            "email" to (prefs[EMAIL] ?: ""),
            "phone" to (prefs[PHONE] ?: ""),
            "address" to (prefs[ADDRESS] ?: ""),
            "gender" to (prefs[GENDER] ?: ""),
            "dob" to (prefs[DOB] ?: "")
        )
    }

    suspend fun saveData(data: Map<String, String>) {
        context.dataStore.edit { prefs ->
            prefs[NAME] = data["name"] ?: ""
            prefs[EMAIL] = data["email"] ?: ""
            prefs[PHONE] = data["phone"] ?: ""
            prefs[ADDRESS] = data["address"] ?: ""
            prefs[GENDER] = data["gender"] ?: ""
            prefs[DOB] = data["dob"] ?: ""
        }
    }
}
