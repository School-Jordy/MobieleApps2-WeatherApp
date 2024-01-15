package com.example.weatherapp.home.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.weatherapp.networking.scheduleApi.ScheduleApiConfig
import com.example.weatherapp.networking.weatherApi.WeatherApiConfig
import com.example.weatherapp.schedule.model.ScheduleResponse
import com.example.weatherapp.utils.Utils
import com.example.weatherapp.weather.model.WeatherForecastResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel : ViewModel() {
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    private val _isError = MutableLiveData<Boolean>()
    val isError: LiveData<Boolean> get() = _isError

    var errorMessage: String = ""
        private set

    val timeBegin = MutableLiveData<String?>()
    val timeEnd = MutableLiveData<String?>()
    val firstHourInt = MutableLiveData<Int>()
    val goingToRain = MutableLiveData<Boolean>()
    val rainingText = MutableLiveData<String>()

    private val cityList = listOf("Heuden-Zolder", "Zonhoven", "Hasselt", "Diepenbeek")

    fun getStartHour(date: String) {
        _isLoading.value = true
        _isError.value = false

        val client = ScheduleApiConfig.getLessonsApiService().getLessons(date = date)

        client.enqueue(object : Callback<ScheduleResponse> {
            override fun onResponse(
                call: Call<ScheduleResponse>,
                response: Response<ScheduleResponse>
            ) {
                val responseBody = response.body()
                if (!response.isSuccessful || responseBody == null) {
                    onError("Data Processing Error")
                    return
                }

                timeBegin.postValue(responseBody.extremeTimes?.begin)
                timeEnd.postValue(responseBody.extremeTimes?.end)

                firstHourInt.postValue(Utils.extractHourFromStingTime(responseBody.extremeTimes?.begin ?: "00:00"))
            }

            override fun onFailure(call: Call<ScheduleResponse>, t: Throwable) {
                onError(t.message)
                t.printStackTrace()
            }
        })
    }

    private fun getWeatherForCity(city: String, firstHour: Int, callback: (Int) -> Unit) {
        val client = WeatherApiConfig.getApiService().getWeatherForecast(location = city)

        client.enqueue(object : Callback<WeatherForecastResponse> {
            override fun onResponse(
                call: Call<WeatherForecastResponse>,
                response: Response<WeatherForecastResponse>
            ) {
                val responseBody = response.body()
                if (!response.isSuccessful || responseBody == null) {
                    onError("Data Processing Error")
                    callback(0)
                    return
                }

                callback(responseBody.forecast?.forecastday?.get(0)?.hour?.get(firstHour)?.willItRain ?: 0)
            }

            override fun onFailure(call: Call<WeatherForecastResponse>, t: Throwable) {
                onError(t.message)
                t.printStackTrace()
                callback(0)
            }
        })
    }

    fun checkWeatherForCities(firstHour: Int) {
        var goingToRainTemp = false
        for (city in cityList) {
            getWeatherForCity(city, firstHour) { willItRain ->
                if (willItRain == 1) {
                    goingToRainTemp = true
                }
            }
        }
        goingToRain.postValue(goingToRainTemp)

        _isLoading.value = false
    }

    private fun onError(inputMessage: String?) {
        val message = if (inputMessage.isNullOrBlank() or inputMessage.isNullOrEmpty()) "Unknown Error"
        else inputMessage

        errorMessage = StringBuilder("ERROR: ")
            .append("$message some data may not displayed properly").toString()

        _isError.value = true
        _isLoading.value = false
    }
}