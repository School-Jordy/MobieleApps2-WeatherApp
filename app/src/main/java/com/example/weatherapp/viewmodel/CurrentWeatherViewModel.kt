package com.example.weatherapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.weatherapp.model.CurrentWeatherResponse
import com.example.weatherapp.networking.weatherApi.WeatherApiConfig
import retrofit2.Callback
import retrofit2.Call
import retrofit2.Response

class CurrentWeatherViewModel() : ViewModel() {

    private val _weatherData = MutableLiveData<CurrentWeatherResponse?>()
    val weatherData: MutableLiveData<CurrentWeatherResponse?> get() = _weatherData

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    private val _isError = MutableLiveData<Boolean>()
    val isError: LiveData<Boolean> get() = _isError

    var errorMessage: String = ""
        private set

    val message = MutableLiveData<String>()
    val name = MutableLiveData<String>()
    val region = MutableLiveData<String>()
    val country = MutableLiveData<String>()
    val localtime = MutableLiveData<String>()
    val condition = MutableLiveData<String>()
    val temperature = MutableLiveData<String>()

    fun getWeatherData(city: String) {

        _isLoading.value = true
        _isError.value = false

        val client = WeatherApiConfig.getApiService().getCurrentWeather(city= city)

        // Send API request using Retrofit
        client.enqueue(object : Callback<CurrentWeatherResponse> {

            override fun onResponse(
                call: Call<CurrentWeatherResponse>,
                response: Response<CurrentWeatherResponse>
            ) {
                val responseBody = response.body()
                if (!response.isSuccessful || responseBody == null) {
                    onError("Data Processing Error")
                    return
                }
                _isLoading.value = false
                name.postValue("Name: ${responseBody.location?.name}")
                region.postValue("Region: ${responseBody.location?.region}")
                country.postValue("Country: ${responseBody.location?.country}")
                localtime.postValue("Local Time: ${responseBody.location?.localtime}")
                condition.postValue("Condition: ${responseBody.current?.condition?.text}")
                temperature.postValue("Temp: ${responseBody.current?.tempC} °C")
//                _weatherData.postValue(responseBody)
            }

            override fun onFailure(call: Call<CurrentWeatherResponse>, t: Throwable) {
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