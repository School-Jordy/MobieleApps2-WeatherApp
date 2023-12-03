package com.example.weatherapp.view

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.R
import com.example.weatherapp.model.HourItem
import com.example.weatherapp.model.LessonsItem
import com.example.weatherapp.model.WeatherForecastResponse

class WeatherForecastAdapter(private val forecastData: WeatherForecastResponse) : RecyclerView.Adapter<WeatherForecastAdapter.ViewHolder>() {
    private var hourItems: List<HourItem> = listOf()
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

    fun updateData(newHourItems: List<HourItem>) {
        this.hourItems = newHourItems
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Log.d("items", hourItems.toString())
        val hourItem = hourItems[position]
        holder.bind(hourItem)
    }

    override fun getItemCount(): Int {
        Log.d("Size", hourItems.size.toString())
        return hourItems.size
    }
}