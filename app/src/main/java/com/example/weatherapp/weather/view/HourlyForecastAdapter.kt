package com.example.weatherapp.weather.view

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.weatherapp.R
import com.example.weatherapp.weather.model.HourItem
import com.example.weatherapp.weather.model.WeatherForecastResponse
import java.text.SimpleDateFormat
import java.util.Locale

class HourlyForecastAdapter(private val forecastData: WeatherForecastResponse) : RecyclerView.Adapter<HourlyForecastAdapter.ViewHolder>() {
    private var hourItems: List<HourItem> = listOf()
    class ViewHolder(inflater: LayoutInflater, parent: ViewGroup) :
        RecyclerView.ViewHolder(inflater.inflate(R.layout.hourly_weather_forecast_item, parent, false)) {
        private var tvHour: TextView? = null
        private var imgCondition: ImageView? = null
        private var tvTemperature: TextView? = null

        init {
            tvHour = itemView.findViewById(R.id.tvHour)
            imgCondition = itemView.findViewById(R.id.img_condition)
            tvTemperature = itemView.findViewById(R.id.tvTemperature)
        }

        fun bind(hourItem: HourItem) {
            val inputFormat = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault())
            val outputFormat = SimpleDateFormat("HH:mm", Locale.getDefault())

            val date = hourItem.time?.let { inputFormat.parse(it) }
            val formattedTime = date?.let { outputFormat.format(it) }

            tvHour?.text = formattedTime
            Glide.with(itemView.context)
                .load("https:${hourItem.condition?.icon}")
                .into(imgCondition!!)
            tvTemperature?.text = hourItem.tempC.toString()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(inflater, parent)
    }

    @SuppressLint("NotifyDataSetChanged")
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