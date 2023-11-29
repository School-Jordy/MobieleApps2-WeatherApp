package com.example.weatherapp.view.weatherForecast

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.weatherapp.R
import com.example.weatherapp.model.HourItem
import com.example.weatherapp.view.weatherForecast.placeholder.PlaceholderContent
import com.example.weatherapp.viewmodel.CurrentWeatherViewModel
import com.example.weatherapp.viewmodel.WeatherForecastViewModel
import java.util.ArrayList

/**
 * A fragment representing a list of Items.
 */
class HourlyForecastFragment : Fragment() {

    private var columnCount = 1
    private lateinit var weatherForecactViewModel: WeatherForecastViewModel
    val hourItems: MutableList<HourItem> = ArrayList()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            columnCount = it.getInt(ARG_COLUMN_COUNT)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_hourly_forecast_list, container, false)

        // Set the adapter
        if (view is RecyclerView) {
            with(view) {
                layoutManager = when {
                    columnCount <= 1 -> LinearLayoutManager(context)
                    else -> GridLayoutManager(context, columnCount)
                }

                weatherForecactViewModel.forecastData.observe(viewLifecycleOwner) { weatherData ->
                    if (weatherData != null) {
                        val index: Int
                        weatherData.forecast?.forecastday?.forEach { forecastDay ->
                            forecastDay?.hour?.forEach { hourItem ->
                                hourItem?.let { hourItems.add(it) }
                            }
                        }
                    }
                }
                adapter = HourlyForcastItemRecyclerViewAdapter(hourItems)
            }
        }
        return view
    }

    companion object {

        // TODO: Customize parameter argument names
        const val ARG_COLUMN_COUNT = "column-count"

        // TODO: Customize parameter initialization
        @JvmStatic
        fun newInstance(columnCount: Int) =
            HourlyForecastFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_COLUMN_COUNT, columnCount)
                }
            }
    }
}