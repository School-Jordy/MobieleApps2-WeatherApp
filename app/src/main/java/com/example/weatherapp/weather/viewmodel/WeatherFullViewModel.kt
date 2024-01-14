package com.example.weatherapp.weather.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.weatherapp.weather.model.CurrentWeatherResponse
import com.example.weatherapp.networking.weatherApi.WeatherApiConfig
import com.example.weatherapp.weather.model.ForecastdayItem
import com.example.weatherapp.weather.model.HourItem
import com.example.weatherapp.weather.model.WeatherForecastResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class WeatherFullViewModel() : ViewModel() {

    private val _weatherData = MutableLiveData<CurrentWeatherResponse?>()
    val weatherData: MutableLiveData<CurrentWeatherResponse?> get() = _weatherData

    private val _hourlyForecastItemList = MutableLiveData<List<HourItem>>()
    val hourlyForecastItemList: LiveData<List<HourItem>?> = _hourlyForecastItemList

    private val _dailyForecastItemList = MutableLiveData<List<ForecastdayItem>>()
    val dailyForecastItemList: LiveData<List<ForecastdayItem>?> = _dailyForecastItemList

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    private val _isError = MutableLiveData<Boolean>()
    val isError: LiveData<Boolean> get() = _isError

    var errorMessage: String = ""
        private set

    val icon = MutableLiveData<String>()
    val region = MutableLiveData<String>()
    val temperature = MutableLiveData<String>()

    private val _iconUrl = MutableLiveData<String>()
    val iconUrl: LiveData<String> get() = _iconUrl
    fun getWeatherData(city: String) {
        _isLoading.value = true
        _isError.value = false

        val currentWeatherClient = WeatherApiConfig.getApiService().getCurrentWeather(city = city)

        // Send API request using Retrofit
        currentWeatherClient.enqueue(object : Callback<CurrentWeatherResponse> {

            override fun onResponse(
                call: Call<CurrentWeatherResponse>,
                response: Response<CurrentWeatherResponse>
            ) {
                val responseBody = response.body()
                if (!response.isSuccessful || responseBody == null) {
                    onError("Data Processing Error")
                    return
                }

                val url = responseBody.current?.condition?.icon.toString()
                _isLoading.value = false
                _iconUrl.postValue(url)
                region.postValue(responseBody.location?.name.toString())
                temperature.postValue("${responseBody.current?.tempC}°C")
            }

            override fun onFailure(call: Call<CurrentWeatherResponse>, t: Throwable) {
                onError(t.message)
                t.printStackTrace()
            }

        })

        val weatherForecastClient = WeatherApiConfig.getApiService().getWeatherForecast(location = city)

        weatherForecastClient.enqueue(object : Callback<WeatherForecastResponse> {
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

                _hourlyForecastItemList.postValue(responseBody.forecast?.forecastday?.get(0)?.hour?.filterNotNull())

                _dailyForecastItemList.postValue(responseBody.forecast?.forecastday?.filterNotNull())
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