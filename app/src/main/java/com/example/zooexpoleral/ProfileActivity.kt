package com.example.zooexpoleral

import android.app.DatePickerDialog
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import java.util.*

class ProfileActivity : AppCompatActivity() {

    private lateinit var userPrefs: UserPreferences

    private lateinit var editName: EditText
    private lateinit var editEmail: EditText
    private lateinit var changeEmail: TextView
    private lateinit var editPhone: EditText
    private lateinit var changePhone: TextView
    private lateinit var editAddress: EditText
    private lateinit var editGender: Spinner
    private lateinit var editDOB: EditText
    private lateinit var saveButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        userPrefs = UserPreferences(this)

        editName = findViewById(R.id.editName)
        editEmail = findViewById(R.id.editEmail)
        changeEmail = findViewById(R.id.changeEmail)
        editPhone = findViewById(R.id.editPhone)
        changePhone = findViewById(R.id.changePhone)
        editAddress = findViewById(R.id.editAddress)
        editGender = findViewById(R.id.editGender)
        editDOB = findViewById(R.id.editDOB)
        saveButton = findViewById(R.id.saveButton)

        setupGenderSpinner()
        setupDOBPicker()
        loadUserProfile()

        saveButton.setOnClickListener {
            val data = mapOf(
                "name" to editName.text.toString(),
                "email" to editEmail.text.toString(),
                "phone" to editPhone.text.toString(),
                "address" to editAddress.text.toString(),
                "gender" to editGender.selectedItem.toString(),
                "dob" to editDOB.text.toString()
            )

            lifecycleScope.launch {
                userPrefs.saveData(data)
                Toast.makeText(this@ProfileActivity, "Profile updated successfully", Toast.LENGTH_SHORT).show()
            }
        }

        changeEmail.setOnClickListener {
            Toast.makeText(this, "Change Email clicked", Toast.LENGTH_SHORT).show()
        }

        changePhone.setOnClickListener {
            Toast.makeText(this, "Change Phone clicked", Toast.LENGTH_SHORT).show()
        }
    }

    private fun setupGenderSpinner() {
        val genderOptions = arrayOf("Select Gender", "Male", "Female", "Other")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, genderOptions)
        editGender.adapter = adapter
    }

    private fun setupDOBPicker() {
        editDOB.setOnClickListener {
            val c = Calendar.getInstance()
            val year = c.get(Calendar.YEAR)
            val month = c.get(Calendar.MONTH)
            val day = c.get(Calendar.DAY_OF_MONTH)

            val datePicker = DatePickerDialog(this, { _, y, m, d ->
                editDOB.setText("$d/${m + 1}/$y")
            }, year, month, day)

            datePicker.show()
        }
    }

    private fun loadUserProfile() {
        lifecycleScope.launch {
            userPrefs.userData.collect { data ->
                editName.setText(data["name"])
                editEmail.setText(data["email"])
                editPhone.setText(data["phone"])
                editAddress.setText(data["address"])
                editDOB.setText(data["dob"])
                val gender = data["gender"]
                val genderOptions = arrayOf("Select Gender", "Male", "Female", "Other")
                val index = genderOptions.indexOf(gender)
                if (index >= 0) {
                    editGender.setSelection(index)
                }
            }
        }
    }
}
