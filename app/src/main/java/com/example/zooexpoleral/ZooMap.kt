package com.example.zooexpoleral

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.example.zooexpoleral.databinding.ActivityZooMapBinding

class ZooMap : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityZooMapBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityZooMapBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // List of zoo attractions with their locations
        val zooAttractions = listOf(
            LatLng(40.7685, -73.9719) to "Zoo Entrance",
            LatLng(40.7698, -73.9731) to "Lion Exhibit",
            LatLng(40.7702, -73.9725) to "Elephant Habitat",
            LatLng(40.7710, -73.9717) to "Giraffe Viewpoint",
            LatLng(40.7690, -73.9705) to "Food Court",
            LatLng(40.7680, -73.9695) to "Rest Area"
        )

        // Add markers for each attraction
        for ((location, title) in zooAttractions) {
            mMap.addMarker(MarkerOptions().position(location).title(title))
        }

        // Move camera to the zoo entrance
        val zooEntrance = zooAttractions[0].first
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(zooEntrance, 15f))
    }
}