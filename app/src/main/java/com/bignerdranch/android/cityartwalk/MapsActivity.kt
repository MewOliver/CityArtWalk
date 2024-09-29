package com.bignerdranch.android.cityartwalk

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.bignerdranch.android.cityartwalk.databinding.ActivityMapsBinding

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapsBinding.inflate(layoutInflater)
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

        // Get the latitude and longitude from the intent
        val latitude = intent.getDoubleExtra("LATITUDE", 0.0)
        val longitude = intent.getDoubleExtra("LONGITUDE", 0.0)

        // Check if the coordinates are valid
        if (latitude != 0.0 && longitude != 0.0) {
            val location = LatLng(latitude, longitude)

            // Add a marker at the provided location and move the camera
            mMap.addMarker(MarkerOptions().position(location).title("Marker at Art Location"))
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 15f)) // Adjust zoom level
        } else {
            // Default behavior if no valid coordinates are provided
            val defaultLocation = LatLng(-34.0, 151.0) // Example: Sydney
            mMap.addMarker(MarkerOptions().position(defaultLocation).title("Marker in Sydney"))
            mMap.moveCamera(CameraUpdateFactory.newLatLng(defaultLocation))
        }
    }
}