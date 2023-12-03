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

class ScheduleListFragment : Fragment() {
    private lateinit var viewModel: ScheduleListViewModel
    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_schedule_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(ScheduleListViewModel::class.java)
        recyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(context)

        viewModel.lessonsList.observe(viewLifecycleOwner) { lessons ->
            recyclerView.adapter = ScheduleAdapter(lessons)
        }

        // Oproepen van de API om de data te laden
        viewModel.getLessonsData("2023-11-30")
    }
}
