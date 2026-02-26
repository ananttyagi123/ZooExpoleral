package com.example.zooexpoleral

import android.os.Parcel
import android.os.Parcelable

data class Animal(
    val name: String,
    val description: String,
    val imageResIds: List<Int>,
    val habitat: String,
    val diet: String,
    val funFacts: String
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.createIntArray()?.toList() ?: emptyList(),
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: ""
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeString(description)
        parcel.writeIntArray(imageResIds.toIntArray())
        parcel.writeString(habitat)
        parcel.writeString(diet)
        parcel.writeString(funFacts)
    }

    override fun describeContents(): Int = 0

    companion object CREATOR : Parcelable.Creator<Animal> {
        override fun createFromParcel(parcel: Parcel): Animal {
            return Animal(parcel)
        }

        override fun newArray(size: Int): Array<Animal?> {
            return arrayOfNulls(size)
        }
    }
}
