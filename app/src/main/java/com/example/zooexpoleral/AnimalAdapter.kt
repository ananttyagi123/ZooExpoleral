package com.example.zooexpoleral

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView

class AnimalAdapter(
    private val context: Context,
    private val animals: List<Animal>
) : RecyclerView.Adapter<AnimalAdapter.AnimalViewHolder>() {

    private var onItemClick: ((Int) -> Unit)? = null

    fun setOnItemClickListener(listener: (Int) -> Unit) {
        onItemClick = listener
    }

    inner class AnimalViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val cardView: CardView = itemView.findViewById(R.id.cardViewAnimal)
        val animalName: TextView = itemView.findViewById(R.id.animalName)
        val animalDescription: TextView = itemView.findViewById(R.id.animalDescription)
        val imageContainer: LinearLayout = itemView.findViewById(R.id.imageContainer)

        init {
            itemView.setOnClickListener {
                onItemClick?.invoke(adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnimalViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_animal, parent, false)
        return AnimalViewHolder(view)
    }

    override fun onBindViewHolder(holder: AnimalViewHolder, position: Int) {
        val animal = animals[position]
        holder.animalName.text = animal.name
        holder.animalDescription.text = animal.description

        holder.imageContainer.removeAllViews()

        for (resId in animal.imageResIds) {
            val imageView = ImageView(context)
            val params = LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f)
            params.setMargins(4, 4, 4, 4)
            imageView.layoutParams = params
            imageView.setImageResource(resId)
            imageView.scaleType = ImageView.ScaleType.CENTER_CROP
            imageView.adjustViewBounds = true
            holder.imageContainer.addView(imageView)
        }
    }

    override fun getItemCount(): Int = animals.size
}
