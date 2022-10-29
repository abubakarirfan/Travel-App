package com.example.weather.repo

import com.example.weather.networking.WeatherInstance

class WeatherRepository(private val instance: WeatherInstance) {

   suspend fun getCurrentWeather(key : String ,query : String,days : Int) = instance.getCurrentTemp(key,query,days)

}