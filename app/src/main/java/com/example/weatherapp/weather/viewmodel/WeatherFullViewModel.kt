package com.example.weatherapp.weather.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.weatherapp.weather.model.CurrentWeatherResponse
import com.example.weatherapp.networking.weatherApi.WeatherApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class WeatherFullViewModel() : ViewModel() {

    private val _weatherData = MutableLiveData<CurrentWeatherResponse?>()
    val weatherData: MutableLiveData<CurrentWeatherResponse?> get() = _weatherData

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

            val client = WeatherApiConfig.getApiService().getCurrentWeather(city = city)

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

                    val url = responseBody.current?.condition?.icon.toString()
                    _isLoading.value = false
                    _iconUrl.postValue(url)

                    region.postValue(responseBody.location?.name.toString())
                    temperature.postValue("${responseBody.current?.tempC} °")
                }

                override fun onFailure(call: Call<CurrentWeatherResponse>, t: Throwable) {
                    onError(t.message)
                    t.printStackTrace()
                }

            })
    }

    private fun getDrawableResourceForWeatherCode(code: Int): String {
        return when (code) {
            1000 -> "day_113"
            1003 -> "day_116"
            1006 -> "day_119"
            1009 -> "day_122"
            1030 -> "day_143"
            1063 -> "day_176"
            1066 -> "day_179"
            1069 -> "day_182"
            1072 -> "day_185"
            1087 -> "day_200"
            1114 -> "day_227"
            1117 -> "day_230"
            1135 -> "day_248"
            1147 -> "day_260"
            1150 -> "day_263"
            1153 -> "day_266"
            1168 -> "day_281"
            1171 -> "day_284"
            1180 -> "day_293"
            1183 -> "day_296"
            1186 -> "day_299"
            1189 -> "day_302"
            1192 -> "day_305"
            1195 -> "day_308"
            1198 -> "day_311"
            1201 -> "day_314"
            1204 -> "day_317"
            1207 -> "day_320"
            1210 -> "day_323"
            1213 -> "day_326"
            1216 -> "day_329"
            1219 -> "day_332"
            1222 -> "day_335"
            1225 -> "day_338"
            1237 -> "day_350"
            1240 -> "day_353"
            1243 -> "day_356"
            1246 -> "day_359"
            1249 -> "day_362"
            1252 -> "day_365"
            1255 -> "day_368"
            1258 -> "day_371"
            1261 -> "day_374"
            1264 -> "day_377"
            1273 -> "day_386"
            1276 -> "day_389"
            1279 -> "day_392"
            1282 -> "day_395"
            else -> "default_icon" // Default case if no match is found
        }
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