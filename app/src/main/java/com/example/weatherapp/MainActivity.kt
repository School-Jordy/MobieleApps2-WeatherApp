package com.example.weatherapp

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.weatherapp.databinding.ActivityMainBinding
import com.example.weatherapp.utils.LocationUtils
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

class MainActivity : AppCompatActivity() {
    private val requestLocationPermissionLauncher = registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions -> }
    private lateinit var locationUtils: LocationUtils

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        locationUtils = LocationUtils(this, requestLocationPermissionLauncher)

        if (locationUtils.checkLocationPermission()) {
            locationUtils.getLastLocation { receivedLatitude, receivedLongitude ->
                latitude = receivedLatitude
                longitude = receivedLongitude
            }
        } else {
            locationUtils.requestLocationPermission { granted ->
                if (granted) {
                    locationUtils.getLastLocation { receivedLatitude, receivedLongitude ->
                        latitude = receivedLatitude
                        longitude = receivedLongitude
                    }
                }
            }
        }

        val navView: BottomNavigationView = binding.navView

        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment_activity_main) as NavHostFragment
        val navController = navHostFragment.navController
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_schedule_list, R.id.navigation_home, R.id.navigation_weather_full
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)

        navView.setupWithNavController(navController)
    }

    fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        binding.navHostFragmentActivityMain.visibility = if (isLoading) View.GONE else View.VISIBLE
    }

    fun showError(context: Context, errorMessage: String) {
        Toast.makeText(context, errorMessage, Toast.LENGTH_LONG).show()
    }

    companion object {
        const val currentDate: String = "2023-11-30"
        var latitude: Double = 50.91
        var longitude: Double = 5.42
    }

}