package com.example.weatherapp.schedule.model

import com.google.gson.annotations.SerializedName

data class LessonsResponse(

    @field:SerializedName("extreme_times")
	val extremeTimes: ExtremeTimes? = null,

    @field:SerializedName("lessons")
	val lessons: List<LessonsItem?>? = null
)

data class LessonsItem(

	@field:SerializedName("date")
	val date: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("end")
	val end: String? = null,

	@field:SerializedName("begin")
	val begin: String? = null
)

data class ExtremeTimes(

	@field:SerializedName("end")
	val end: String? = null,

	@field:SerializedName("begin")
	val begin: String? = null
)

