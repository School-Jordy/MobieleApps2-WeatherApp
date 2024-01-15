package com.example.weatherapp.home.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.databinding.DataBindingUtil
import com.example.weatherapp.MainActivity
import com.example.weatherapp.R
import com.example.weatherapp.databinding.FragmentHomeBinding
import com.example.weatherapp.home.viewmodel.HomeViewModel


class HomeFragment : Fragment() {
    private lateinit var homeViewModel: HomeViewModel
    private lateinit var binding: FragmentHomeBinding
    private lateinit var imgCondition: ImageView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = DataBindingUtil.bind(view)!!
        homeViewModel = HomeViewModel()
        binding.viewModel = homeViewModel
        binding.lifecycleOwner = viewLifecycleOwner

        homeViewModel.getStartHour(MainActivity.currentDate)

        imgCondition = view.findViewById(R.id.img_vehicle)

        subscribe()
    }

    private fun subscribe(){
        homeViewModel.firstHourInt.observe(viewLifecycleOwner) { firstHour ->
            homeViewModel.checkWeatherForCities(firstHour)
        }

        homeViewModel.goingToRain.observe(viewLifecycleOwner) { goingToRain ->
            if (goingToRain) {
                imgCondition.setImageResource(R.drawable.baseline_directions_car_filled_24)
                homeViewModel.rainingText.postValue(context?.getString(R.string.going_to_rain))
            } else {
                imgCondition.setImageResource(R.drawable.baseline_pedal_bike_24)
                homeViewModel.rainingText.postValue(context?.getString(R.string.not_going_to_rain))
            }
        }

        homeViewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            (activity as? MainActivity)?.showLoading(isLoading)
        }

        homeViewModel.isError.observe(viewLifecycleOwner) { isError ->
            if (isError) {
                context?.let { (activity as? MainActivity)?.showError(it, homeViewModel.errorMessage) }
            }
        }
    }
}