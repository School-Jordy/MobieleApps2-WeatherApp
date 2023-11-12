package com.example.weatherapp.viewmodel
import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.weatherapp.model.ApiService

class MyViewModel : ViewModel() {
    private val _data = MutableLiveData<String>()
    val data: LiveData<String> = _data

    fun loadData(context: Context) {
        ApiService(context).getData("https://catfact.ninja/fact",
            onSuccess = { response ->
                _data.value = response
                // log the respone to the console
                println(response)
            },
            onError = { error ->
                // Foutafhandeling
            })
    }
}