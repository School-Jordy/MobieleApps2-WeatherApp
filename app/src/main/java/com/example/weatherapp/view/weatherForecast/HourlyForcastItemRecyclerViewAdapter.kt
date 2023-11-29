package com.example.weatherapp.view.weatherForecast

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.weatherapp.R

import com.example.weatherapp.view.weatherForecast.placeholder.PlaceholderContent.PlaceholderItem
import com.example.weatherapp.databinding.FragmentHourlyForecastBinding
import com.example.weatherapp.model.HourItem

/**
 * [RecyclerView.Adapter] that can display a [PlaceholderItem].
 * TODO: Replace the implementation with code for your data type.
 */
class HourlyForcastItemRecyclerViewAdapter(
    private val values: List<HourItem>
) : RecyclerView.Adapter<HourlyForcastItemRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(
            FragmentHourlyForecastBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]
        holder.idView.text = "0"
        holder.contentView.text = item.tempC.toString()
    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(binding: FragmentHourlyForecastBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val idView: TextView = binding.itemNumber
        val contentView: TextView = binding.content

        override fun toString(): String {
            return super.toString() + " '" + contentView.text + "'"
        }
    }

}