package com.example.weatherapp.networking.scheduleApi

import com.example.weatherapp.schedule.model.ScheduleResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ScheduleApiService {
    @GET("lessons")
    fun getLessons(
        @Query("date") date: String,
    ): Call<ScheduleResponse>
}