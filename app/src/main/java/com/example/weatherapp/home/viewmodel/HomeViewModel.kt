package com.example.weatherapp.home.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.weatherapp.networking.scheduleApi.ScheduleApiConfig
import com.example.weatherapp.networking.weatherApi.WeatherApiConfig
import com.example.weatherapp.schedule.model.LessonsResponse
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

    val firstHourFull = MutableLiveData<String>()
    var firstHourInt = MutableLiveData<Int>()

    fun getStartHour(date: String) {
        _isLoading.value = true
        _isError.value = false

        val client = ScheduleApiConfig.getLessonsApiService().getLessons(date = date)

        client.enqueue(object : Callback<LessonsResponse> {
            override fun onResponse(
                call: Call<LessonsResponse>,
                response: Response<LessonsResponse>
            ) {
                val responseBody = response.body()
                if (!response.isSuccessful || responseBody == null) {
                    onError("Data Processing Error")
                    return
                }
                _isLoading.value = false
                Log.d("HomeViewModel", "firstHour: ${responseBody.lessons?.get(0)?.begin.toString()}")

                firstHourFull.postValue(responseBody.lessons?.get(0)?.begin.toString())

                firstHourInt.postValue(extractHourFromStingTime(responseBody.lessons?.get(0)?.begin.toString()))
            }

            override fun onFailure(call: Call<LessonsResponse>, t: Throwable) {
                onError(t.message)
                t.printStackTrace()
            }
        })
    }

    fun getWeatherForHour(firstHour: Int){
        _isLoading.value = true
        _isError.value = false

        val client = WeatherApiConfig.getApiService().getWeatherForecast(location= "Heusden-Zolder")

        client.enqueue(object : Callback<WeatherForecastResponse> {
            override fun onResponse(
                call: Call<WeatherForecastResponse>,
                response: Response<WeatherForecastResponse>
            ) {
                val responseBody = response.body()
                if (!response.isSuccessful || responseBody == null) {
                    onError("Data Processing Error")
                    return
                }
                _isLoading.value = false
                Log.d("HomeViewModel", "firstHour: ${responseBody.forecast?.forecastday?.get(0)?.hour?.get(firstHour)?.willItRain.toString()}")
            }

            override fun onFailure(call: Call<WeatherForecastResponse>, t: Throwable) {
                onError(t.message)
                t.printStackTrace()
            }
        })
    }

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