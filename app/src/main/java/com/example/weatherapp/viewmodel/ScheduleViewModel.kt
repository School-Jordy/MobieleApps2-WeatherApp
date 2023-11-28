package com.example.weatherapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.weatherapp.model.LessonsResponse
import com.example.weatherapp.networking.ScheduleApiConfig
import retrofit2.Callback
import retrofit2.Call
import retrofit2.Response
class ScheduleViewModel {
    private val _lessonsData = MutableLiveData<LessonsResponse>()
    val lessonsData: LiveData<LessonsResponse> get() = _lessonsData
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading
    private val _isError = MutableLiveData<Boolean>()
    val isError: LiveData<Boolean> get() = _isError
    var errorMessage: String = ""
        private set

    fun getLessonsData(date: String) {
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
                _lessonsData.postValue(responseBody)
            }

            override fun onFailure(call: Call<LessonsResponse>, t: Throwable) {
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