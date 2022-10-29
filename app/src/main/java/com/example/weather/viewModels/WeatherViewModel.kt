package com.example.weather.viewModels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.weather.modals.WeatherClass
import com.example.weather.repo.WeatherRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class WeatherViewModel(private val repository: WeatherRepository) : ViewModel() {

    val weather : MutableLiveData<WeatherClass> = MutableLiveData()

    fun getCurrTemp(key : String ,query : String,days : Int){
        CoroutineScope(Dispatchers.IO).launch {
            val response = repository.getCurrentWeather(key,query,days)
                withContext(Dispatchers.Main) {
                    if (response.isSuccessful) {
                        weather.value = response.body()
                    } else {
                        Log.d("Error:", "Could not fetch data")
                    }
                }
        }
    }
}