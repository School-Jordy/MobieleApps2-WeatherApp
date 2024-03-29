package com.example.weatherapp.schedule.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.weatherapp.schedule.model.LessonsItem
import com.example.weatherapp.schedule.model.ScheduleResponse
import com.example.weatherapp.networking.scheduleApi.ScheduleApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ScheduleViewModel : ViewModel() {
    private val _lessonsList = MutableLiveData<List<LessonsItem>>()
    val lessonsList: LiveData<List<LessonsItem>> = _lessonsList

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    private val _isError = MutableLiveData<Boolean>()
    val isError: LiveData<Boolean> get() = _isError

    var errorMessage: String = ""
        private set

    fun getLessonsData(date: String) {
        _isLoading.value = true
        _isError.value = false

        val scheduleClient = ScheduleApiConfig.getLessonsApiService().getLessons(date = date)

        scheduleClient.enqueue(object : Callback<ScheduleResponse> {
            override fun onResponse(
                call: Call<ScheduleResponse>,
                response: Response<ScheduleResponse>
            ) {
                val responseBody = response.body()
                if (!response.isSuccessful || responseBody == null) {
                    onError("Data Processing Error")
                    return
                }
                _isLoading.value = false
                _lessonsList.postValue(responseBody.lessons?.filterNotNull())
            }

            override fun onFailure(call: Call<ScheduleResponse>, t: Throwable) {
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
