package com.example.weatherapp.networking

import com.example.weatherapp.model.LessonsResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ScheduleApiService {
    @GET("lessons")
    fun getLessons(
        @Query("date") date: String,
    ): Call<LessonsResponse>
}