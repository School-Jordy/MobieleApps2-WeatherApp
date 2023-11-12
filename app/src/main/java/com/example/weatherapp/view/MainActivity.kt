package com.example.weatherapp.view

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.weatherapp.R
import com.example.weatherapp.viewmodel.MyViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: MyViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val tvResult = findViewById<TextView>(R.id.tv_result)
        val btnGet = findViewById<Button>(R.id.btn_get)
        val loading = findViewById<ProgressBar>(R.id.loading)

        btnGet.setOnClickListener {
            loading.visibility = View.VISIBLE
            val url = "https://catfact.ninja/fact"
            val queue: RequestQueue = Volley.newRequestQueue(this)
            val request = JsonObjectRequest(Request.Method.GET, url, null, { response ->
                loading.visibility = View.INVISIBLE
                try {
                    val fact = response.getString("fact")

                    tvResult.text = fact
                } catch (e: Exception) {
                    val msg = "error: ${e.message}"
                    tvResult.text = msg
                }
            }, { error ->
                loading.visibility = View.INVISIBLE
                tvResult.text = error.message
        })
            queue.add(request)
        }
    }
}