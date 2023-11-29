package com.example.weatherapp.view

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.R
import com.example.weatherapp.model.HourItem
import com.example.weatherapp.model.LessonsItem
import com.example.weatherapp.model.WeatherForecastResponse

class WeatherForecastAdapter(private val forecastData: WeatherForecastResponse) : RecyclerView.Adapter<WeatherForecastAdapter.ViewHolder>() {
    class ViewHolder(inflater: LayoutInflater, parent: ViewGroup) :
        RecyclerView.ViewHolder(inflater.inflate(R.layout.hourly_weather_forecast_item, parent, false)) {
        private var tvTemperature: TextView? = null

        init {
            tvTemperature = itemView.findViewById(R.id.tvTemperature)
        }

        fun bind(hourItem: HourItem) {
            tvTemperature?.text = hourItem.tempC.toString()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(inflater, parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val lesson: LessonsItem = lessonsList[position]
        holder.bind(lesson)
    }

    override fun getItemCount(): Int = lessonsList.size
}