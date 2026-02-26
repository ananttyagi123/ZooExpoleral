package com.example.zooexpoleral

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2

class AnimalDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_animal_detail)

        val animal = intent.getParcelableExtra<Animal>("animal")

        val viewPager = findViewById<ViewPager2>(R.id.imageViewPager)
        val nameTextView = findViewById<TextView>(R.id.animalNameTextView)
        val descriptionTextView = findViewById<TextView>(R.id.animalDescriptionTextView)
        val additionalDetailsTextView = findViewById<TextView>(R.id.animalAdditionalDetailsTextView)

        animal?.let {
            nameTextView.text = it.name
            descriptionTextView.text = it.description

            additionalDetailsTextView.text = "Habitat: ${it.habitat}\nDiet: ${it.diet}\nFun Facts: ${it.funFacts}"

            val imageAdapter = ImageAdapter(this, it.imageResIds)
            viewPager.adapter = imageAdapter
        }
    }
}