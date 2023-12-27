package com.example.weatherapp.weather.view

import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.app.ActivityCompat
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.example.weatherapp.R
import com.example.weatherapp.databinding.FragmentWeatherFullBinding
import com.example.weatherapp.utils.LocationUtils
import com.example.weatherapp.MainActivity
import com.example.weatherapp.weather.viewmodel.WeatherFullViewModel
import com.google.android.gms.location.FusedLocationProviderClient

class WeatherFullFragment : Fragment() {
    private lateinit var weatherFullViewModel: WeatherFullViewModel
    private lateinit var imgCondition: ImageView
    private lateinit var tvResult: TextView
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private lateinit var binding: FragmentWeatherFullBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_weather_full, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        checkLocationPermission()

        binding = DataBindingUtil.bind(view)!!

        weatherFullViewModel = WeatherFullViewModel()

        binding.viewModel = weatherFullViewModel
        binding.lifecycleOwner = viewLifecycleOwner
        subscribe()

        imgCondition = view.findViewById(R.id.img_condition)


    }

    companion object {
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1
    }

    private fun subscribe() {
        weatherFullViewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            (activity as? MainActivity)?.showLoading(isLoading)
        }

        weatherFullViewModel.isError.observe(viewLifecycleOwner) { isError ->
            if (isError) {
                imgCondition.visibility = View.GONE
            }
        }

        weatherFullViewModel.weatherData.observe(viewLifecycleOwner) { weatherData ->
        }

        weatherFullViewModel.iconUrl.observe(viewLifecycleOwner) { url ->
            url?.let {
                Log.d("WeatherFullViewModel", "onResponse: $imgCondition")
                Glide.with(this)
                    .load("https:$it")
                    .override(64, 64)
                    .placeholder(R.drawable.baseline_cloud_24)
                    .into(imgCondition)
            }
        }
    }

    private fun setResultImage(imageUrl: String?) {
        imageUrl?.let { url ->
            Glide.with(this)
                .load("https:$url")
                .into(imgCondition)

            imgCondition.visibility = View.VISIBLE
        } ?: run {
            imgCondition.visibility = View.GONE
        }
    }

    // Voeg de volgende functies toe in de fragment-klasse
    private fun checkLocationPermission() {
        if (ActivityCompat.checkSelfPermission(requireContext(), android.Manifest.permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), LOCATION_PERMISSION_REQUEST_CODE)
        } else {
            getLocationAndFetchWeather()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when (requestCode) {
            LOCATION_PERMISSION_REQUEST_CODE -> {
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    getLocationAndFetchWeather()
                } else {
                }
            }
        }
    }

    private fun getLocationAndFetchWeather() {
        val locationUtils = LocationUtils(requireContext())
        locationUtils.getLastLocation { latLng ->
            weatherFullViewModel.getWeatherData(latLng)
        }
    }
}