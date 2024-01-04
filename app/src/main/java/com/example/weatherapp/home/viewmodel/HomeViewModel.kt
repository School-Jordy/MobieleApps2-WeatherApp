package com.example.weatherapp.home.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.weatherapp.networking.scheduleApi.ScheduleApiConfig
import com.example.weatherapp.networking.weatherApi.WeatherApiConfig
import com.example.weatherapp.schedule.model.LessonsItem
import com.example.weatherapp.schedule.model.LessonsResponse
import com.example.weatherapp.weather.model.CurrentWeatherResponse
import com.example.weatherapp.weather.model.HourItem
import com.example.weatherapp.weather.model.WeatherForecastResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel : ViewModel() {
    var firstHour: Int = -1

    private val _hourWeather = MutableLiveData<HourItem>()
    val hourWeather: LiveData<HourItem> = _hourWeather

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    private val _isError = MutableLiveData<Boolean>()
    val isError: LiveData<Boolean> get() = _isError

    var errorMessage: String = ""
        private set



    private fun onError(inputMessage: String?) {
        val message = if (inputMessage.isNullOrBlank() or inputMessage.isNullOrEmpty()) "Unknown Error"
        else inputMessage

        errorMessage = StringBuilder("ERROR: ")
            .append("$message some data may not displayed properly").toString()

        _isError.value = true
        _isLoading.value = false
    }

    fun extractHourFromStingTime(time: String): Int {
        val hour = time.split(":")[0]
        return hour.toInt()
    }
}