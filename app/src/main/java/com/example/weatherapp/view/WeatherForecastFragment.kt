package com.example.weatherapp.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.R
import com.example.weatherapp.viewmodel.ScheduleListViewModel
import com.example.weatherapp.viewmodel.WeatherForecastViewModel

class WeatherForecastFragment : Fragment() {
    private lateinit var viewModel: WeatherForecastViewModel
    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_weather_forecast, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(WeatherForecastViewModel::class.java)
        recyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(context)

        viewModel.forecastData.observe(viewLifecycleOwner) { forecastData ->
            forecastData?.let {
                recyclerView.adapter = WeatherForecastAdapter(it)
            }
        }

        viewModel.getForecastData("Heusden-zolder")
    }
}