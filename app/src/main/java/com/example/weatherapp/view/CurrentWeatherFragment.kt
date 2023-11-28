package com.example.weatherapp.view

import android.content.pm.PackageManager
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.core.app.ActivityCompat
import com.example.weatherapp.R
import com.example.weatherapp.model.CurrentWeatherResponse
import com.example.weatherapp.viewmodel.CurrentWeatherViewModel
import com.bumptech.glide.Glide
import com.example.weatherapp.utils.LocationUtils
import com.google.android.gms.location.FusedLocationProviderClient
import java.lang.StringBuilder

class CurrentWeatherFragment : Fragment() {

    private lateinit var currentWeatherViewModel: CurrentWeatherViewModel
    private lateinit var imgCondition: ImageView
    private lateinit var tvResult: TextView
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_current_weather, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        currentWeatherViewModel = CurrentWeatherViewModel()
        subscribe()

        imgCondition = view.findViewById(R.id.img_condition)
        tvResult = view.findViewById(R.id.tv_result)

        checkLocationPermission()
    }

    companion object {
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1
    }

    private fun subscribe() {
        currentWeatherViewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            if (isLoading) tvResult.text = getString(R.string.loading)
        }

        currentWeatherViewModel.isError.observe(viewLifecycleOwner) { isError ->
            if (isError) {
                imgCondition.visibility = View.GONE
                tvResult.text = currentWeatherViewModel.errorMessage
            }
        }

        currentWeatherViewModel.weatherData.observe(viewLifecycleOwner) { weatherData ->
            setResultText(weatherData)
        }
    }

    private fun setResultText(weatherData: CurrentWeatherResponse) {
        val resultText = StringBuilder("Result:\n")

        weatherData.location.let { location ->
            resultText.append("Name: ${location?.name}\n")
            resultText.append("Region: ${location?.region}\n")
            resultText.append("Country: ${location?.country}\n")
            resultText.append("Timezone ID: ${location?.tzId}\n")
            resultText.append("Local Time: ${location?.localtime}\n")
        }

        weatherData.current.let { current ->
            current?.condition.let { condition ->
                resultText.append("Condition: ${condition?.text}\n")
                setResultImage(condition?.icon)
            }
            resultText.append("Celcius: ${current?.tempC}\n")
            resultText.append("Fahrenheit: ${current?.tempF}\n")
        }

        tvResult.text = resultText
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
                    tvResult.text = "Location permission is required to fetch weather data."
                }
            }
        }
    }

    private fun getLocationAndFetchWeather() {
        val locationUtils = LocationUtils(requireContext())
        locationUtils.getLastLocation { latLng ->
            currentWeatherViewModel.getWeatherData(latLng)
        }
    }
}
