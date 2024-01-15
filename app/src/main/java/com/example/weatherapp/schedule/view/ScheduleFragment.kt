package com.example.weatherapp.schedule.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.R
import com.example.weatherapp.schedule.viewmodel.ScheduleViewModel
import com.example.weatherapp.MainActivity

class ScheduleListFragment : Fragment() {
    private lateinit var scheduleViewModel: ScheduleViewModel
    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_schedule, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        scheduleViewModel = ViewModelProvider(this)[ScheduleViewModel::class.java]
        recyclerView = view.findViewById(R.id.scheduleRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(context)

        subscribe()

        scheduleViewModel.getLessonsData(MainActivity.currentDate)
    }

    private fun subscribe() {
        scheduleViewModel.lessonsList.observe(viewLifecycleOwner) { lessons ->
            recyclerView.adapter = ScheduleAdapter(lessons)
        }

        scheduleViewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            (activity as? MainActivity)?.showLoading(isLoading)
        }

        scheduleViewModel.isError.observe(viewLifecycleOwner) { isError ->
            if (isError) {
                context?.let { (activity as? MainActivity)?.showError(it, scheduleViewModel.errorMessage) }
            }
        }
    }
}