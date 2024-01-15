package com.example.weatherapp.weather.view

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.weatherapp.databinding.HourlyWeatherForecastItemBinding
import com.example.weatherapp.utils.Utils
import com.example.weatherapp.weather.model.HourItem

class HourlyForecastAdapter :
    RecyclerView.Adapter<HourlyForecastAdapter.ViewHolder>() {

    private var hourItems: List<HourItem> = listOf()

    class ViewHolder(private val binding: HourlyWeatherForecastItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(hourItem: HourItem) {
            val formattedTime = Utils.getHourOnlyFromHourItem(hourItem)

            binding.tvHour.text = formattedTime
            Glide.with(itemView.context)
                .load("https:${hourItem.condition?.icon}")
                .into(binding.imgCondition)
            binding.tvTemperature.text = hourItem.tempC.toString()
        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = HourlyWeatherForecastItemBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateData(newHourItems: List<HourItem>) {
        this.hourItems = newHourItems
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val hourItem = hourItems[position]
        holder.bind(hourItem)
    }

    override fun getItemCount(): Int {
        return hourItems.size
    }
}
