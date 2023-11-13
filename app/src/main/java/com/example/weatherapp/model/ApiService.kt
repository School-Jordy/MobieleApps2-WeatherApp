package com.example.weatherapp.model

import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import org.json.JSONObject

class ApiService(private val requestQueue: RequestQueue) {
    fun getCatFact(successCallback: (String) -> Unit, errorCallback: (String?) -> Unit) {
        val url = "https://catfact.ninja/fact"
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, url, null,
            { response: JSONObject ->
                successCallback(response.getString("fact"))
            },
            { error ->
                errorCallback(error.message)
            }
        )
        requestQueue.add(jsonObjectRequest)
    }
}
