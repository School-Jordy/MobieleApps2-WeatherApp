package com.example.weatherapp.model
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import android.content.Context

class ApiService(private val context: Context) {
    fun getData(url: String, onSuccess: (String) -> Unit, onError: (String) -> Unit) {
        val queue = Volley.newRequestQueue(context)
        val stringRequest = StringRequest(Request.Method.GET, url,
            { response -> onSuccess(response) },
            { error -> onError(error.toString()) })

        queue.add(stringRequest)
    }
}