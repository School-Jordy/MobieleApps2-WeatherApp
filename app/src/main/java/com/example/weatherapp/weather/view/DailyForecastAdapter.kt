package com.example.weatherapp.weather.view

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.R
import com.example.weatherapp.databinding.DailyWeatherForecastItemBinding
import com.example.weatherapp.utils.Utils
import com.example.weatherapp.weather.model.ForecastdayItem

class DailyForecastAdapter : RecyclerView.Adapter<DailyForecastAdapter.ViewHolder>() {
    private var dayItems: List<ForecastdayItem> = listOf()
    inner class ViewHolder(private val binding: DailyWeatherForecastItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(dayItem: ForecastdayItem) {
            binding.apply {
                tvDay.text = Utils.getShortDayFromDayItem(dayItem)
                tvChanceOfRain.text = "${dayItem.day?.dailyChanceOfRain}%"
                tvMinMax.text = "${dayItem.day?.maxtempC}°/ ${dayItem.day?.mintempC}°"
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = DailyWeatherForecastItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateData(newDayItems: List<ForecastdayItem>) {
        dayItems = newDayItems.toMutableList()
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val dayItem = dayItems[position]
        holder.bind(dayItem)
    }

    override fun getItemCount(): Int {
        return dayItems.size
    }
}