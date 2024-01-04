package com.example.weatherapp.home.view

import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.databinding.DataBindingUtil
import com.example.weatherapp.R
import com.example.weatherapp.databinding.FragmentCurrentWeatherBinding
import com.example.weatherapp.databinding.FragmentHomeBinding
import com.example.weatherapp.home.viewmodel.HomeViewModel
import com.example.weatherapp.schedule.viewmodel.ScheduleViewModel
import com.example.weatherapp.utils.LocationUtils
import com.example.weatherapp.weather.view.CurrentWeatherFragment
import com.example.weatherapp.weather.viewmodel.CurrentWeatherViewModel


class HomeFragment : Fragment() {
    private lateinit var homeViewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}