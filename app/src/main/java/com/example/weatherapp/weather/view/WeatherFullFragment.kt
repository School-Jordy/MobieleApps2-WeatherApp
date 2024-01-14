package com.example.weatherapp.weather.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.weatherapp.R
import com.example.weatherapp.databinding.FragmentWeatherFullBinding
import com.example.weatherapp.MainActivity
import com.example.weatherapp.weather.model.WeatherForecastResponse
import com.example.weatherapp.weather.viewmodel.WeatherFullViewModel

class WeatherFullFragment : Fragment() {
    private lateinit var weatherFullViewModel: WeatherFullViewModel
    private lateinit var imgCondition: ImageView
    private lateinit var tvResult: TextView
    private lateinit var binding: FragmentWeatherFullBinding
    private lateinit var hourlyForecastRecyclerView: RecyclerView
    private lateinit var dailyForecastRecyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_weather_full, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = DataBindingUtil.bind(view)!!

        weatherFullViewModel = WeatherFullViewModel()

        binding.viewModel = weatherFullViewModel
        binding.lifecycleOwner = viewLifecycleOwner

        hourlyForecastRecyclerView = view.findViewById(R.id.hourlyForecastRecyclerView)
        hourlyForecastRecyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        hourlyForecastRecyclerView.adapter = HourlyForecastAdapter(WeatherForecastResponse())

        dailyForecastRecyclerView = view.findViewById(R.id.dailyForecastRecyclerView)
        dailyForecastRecyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        dailyForecastRecyclerView.adapter = DailyForecastAdapter(WeatherForecastResponse())

        subscribe()

        weatherFullViewModel.getWeatherData("${MainActivity.latitude},${MainActivity.longitude}")

        imgCondition = view.findViewById(R.id.img_condition)


    }

    private fun subscribe() {
        weatherFullViewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            (activity as? MainActivity)?.showLoading(isLoading)
        }

        weatherFullViewModel.isError.observe(viewLifecycleOwner) { isError ->
            if (isError) {
                imgCondition.visibility = View.GONE
            }
        }

        weatherFullViewModel.iconUrl.observe(viewLifecycleOwner) { url ->
            url?.let {
                Glide.with(this)
                    .load("https:$it")
                    .override(64, 64)
                    .into(imgCondition)
            }
        }

        weatherFullViewModel.hourlyForecastItemList.observe(viewLifecycleOwner) { hourItemList ->
            hourItemList?.let {
                (hourlyForecastRecyclerView.adapter as? HourlyForecastAdapter)?.updateData(it)
            }
        }

        weatherFullViewModel.dailyForecastItemList.observe(viewLifecycleOwner) { dayItemList ->
            dayItemList?.let {
                (dailyForecastRecyclerView.adapter as? DailyForecastAdapter)?.updateData(it)
            }
        }
    }
}