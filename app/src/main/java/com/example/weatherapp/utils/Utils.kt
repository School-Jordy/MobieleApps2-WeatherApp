package com.example.weatherapp.utils

import com.example.weatherapp.weather.model.ForecastdayItem
import com.example.weatherapp.weather.model.HourItem
import java.text.SimpleDateFormat
import java.util.Locale

object Utils {
    fun getShortDayFromDayItem(dayItem: ForecastdayItem): String? {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val date = dayItem.date?.let { inputFormat.parse(it) }
        val outputFormat = SimpleDateFormat("EE", Locale.getDefault())
        return date?.let { outputFormat.format(it) }
    }

    fun getHourOnlyFromHourItem(hourItem: HourItem): String? {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault())
        val outputFormat = SimpleDateFormat("HH:mm", Locale.getDefault())

        val date = hourItem.time?.let { inputFormat.parse(it) }
        return date?.let { outputFormat.format(it) }
    }

    fun extractHourFromStingTime(time: String): Int {
        val hour = time.split(":")[0]
        return hour.toInt()
    }
}