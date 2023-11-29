package com.example.weatherapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.weatherapp.model.WeatherForecastResponse
import com.example.weatherapp.networking.weatherApi.WeatherApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class WeatherForecastViewModel : ViewModel() {
    private val _forecastData = MutableLiveData<WeatherForecastResponse?>()
    val forecastData: MutableLiveData<WeatherForecastResponse?> get() = _forecastData

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    private val _isError = MutableLiveData<Boolean>()
    val isError: LiveData<Boolean> get() = _isError

    var errorMessage: String = ""
        private set

    val forecastDataText = MutableLiveData<String>()

    fun getForecastData(location: String, days: Int){
        _isLoading.value = true
        _isError.value = false

        val client = WeatherApiConfig.getApiService().getWeatherForecast(location= location, days = days)

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
                forecastDataText.postValue("name: ${responseBody.location?.name}")
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
}