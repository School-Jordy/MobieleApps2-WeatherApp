package com.example.weatherapp.schedule.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.weatherapp.R
import com.example.weatherapp.schedule.model.LessonsResponse
import com.example.weatherapp.schedule.viewmodel.ScheduleViewModel
import kotlin.text.StringBuilder

class ScheduleFragment : Fragment() {
    private lateinit var scheduleViewModel: ScheduleViewModel
    private lateinit var tvResult: TextView
    private lateinit var btnSend: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_lessons, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        scheduleViewModel = ScheduleViewModel()
        subscribe()

        tvResult = view.findViewById(R.id.tv_lessons)
//        btnSend = view.findViewById(R.id.btn_send_request)

//        btnSend.setOnClickListener {
        scheduleViewModel.getLessonsData("2023-11-30")
//        }
    }

    private fun subscribe() {
        scheduleViewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            if (isLoading) tvResult.text = getString(R.string.loading)
        }

        scheduleViewModel.isError.observe(viewLifecycleOwner) { isError ->
            if (isError) tvResult.text = scheduleViewModel.errorMessage
        }

        scheduleViewModel.lessonsData.observe(viewLifecycleOwner) { lessonsData ->
            setResultText(lessonsData)
        }
    }

    private fun setResultText(lessonsData: LessonsResponse) {
        val resultText = StringBuilder("Result:\n")
        lessonsData.lessons?.forEach { lessonItem ->
            lessonItem?.let {
                resultText.append("Name: ${it.name}\n")
                resultText.append("Date: ${it.date}\n")
                resultText.append("Begin Time: ${it.begin}\n")
                resultText.append("End Time: ${it.end}\n\n")
            }
        }

        tvResult.text = resultText
    }
}