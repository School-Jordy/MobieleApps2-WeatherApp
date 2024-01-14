package com.example.weatherapp.utils

object Utils {
    fun extractHourFromStingTime(time: String): Int {
        val hour = time.split(":")[0]
        return hour.toInt()
    }
}