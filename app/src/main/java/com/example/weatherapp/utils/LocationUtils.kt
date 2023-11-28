package com.example.weatherapp.utils
import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.tasks.Task

class LocationUtils(private val context: Context) {

    private var fusedLocationProviderClient: FusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(context)

    @SuppressLint("MissingPermission")
    fun getLastLocation(onLocationReceived: (String) -> Unit) {
        val task: Task<Location> = fusedLocationProviderClient.lastLocation
        task.addOnSuccessListener { location: Location? ->
            location?.let {
                val latLng = "${location.latitude},${location.longitude}"
                onLocationReceived(latLng)
            } ?: run {
                onLocationReceived("Location not found")
            }
        }
        task.addOnFailureListener {
            onLocationReceived("Failed to get location")
        }
    }
}