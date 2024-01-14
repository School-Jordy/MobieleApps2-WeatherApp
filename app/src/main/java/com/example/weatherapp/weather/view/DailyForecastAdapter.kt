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
import com.example.weatherapp.weather.model.ForecastdayItem
import com.example.weatherapp.weather.model.HourItem
import com.example.weatherapp.weather.model.WeatherForecastResponse
import java.text.SimpleDateFormat
import java.util.Locale

class DailyForecastAdapter(private val forecastData: WeatherForecastResponse) : RecyclerView.Adapter<DailyForecastAdapter.ViewHolder>() {
    private var dayItems: List<ForecastdayItem> = listOf()
    class ViewHolder(inflater: LayoutInflater, parent: ViewGroup) :
        RecyclerView.ViewHolder(inflater.inflate(R.layout.hourly_weather_forecast_item, parent, false)) {
        private var tvDate: TextView? = null
        private var imgCondition: ImageView? = null
        private var tvTemperature: TextView? = null

        init {
            tvDate = itemView.findViewById(R.id.tvDate)
        }

        fun bind(dayItem: ForecastdayItem) {
            tvDate?.text = dayItem.date
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(inflater, parent)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateData(newDayItems: List<ForecastdayItem>) {
        this.dayItems = newDayItems
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Log.d("items", dayItems.toString())
        val dayItem = dayItems[position]
        holder.bind(dayItem)
    }

    override fun getItemCount(): Int {
        Log.d("Size", dayItems.size.toString())
        return dayItems.size
    }
}