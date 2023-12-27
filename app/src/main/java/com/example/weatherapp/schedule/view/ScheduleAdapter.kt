package com.example.weatherapp.schedule.view

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.schedule.model.LessonsItem
import com.example.weatherapp.R

class ScheduleAdapter(private val lessonsList: List<LessonsItem>) : RecyclerView.Adapter<ScheduleAdapter.ViewHolder>() {

    class ViewHolder(inflater: LayoutInflater, parent: ViewGroup) :
        RecyclerView.ViewHolder(inflater.inflate(R.layout.lesson_item, parent, false)) {
        private var tvName: TextView? = null
        private var tvDate: TextView? = null
        private var tvBegin: TextView? = null
        private var tvEnd: TextView? = null

        init {
            tvName = itemView.findViewById(R.id.tvName)
            tvDate = itemView.findViewById(R.id.tvDate)
            tvBegin = itemView.findViewById(R.id.tvBegin)
            tvEnd = itemView.findViewById(R.id.tvEnd)
        }

        fun bind(lesson: LessonsItem) {
            tvName?.text = lesson.name
            tvDate?.text = lesson.date
            tvBegin?.text = lesson.begin
            tvEnd?.text = lesson.end
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(inflater, parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val lesson: LessonsItem = lessonsList[position]
        holder.bind(lesson)
    }

    override fun getItemCount(): Int = lessonsList.size
}
