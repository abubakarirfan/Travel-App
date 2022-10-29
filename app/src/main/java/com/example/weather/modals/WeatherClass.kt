package com.example.weather.modals

data class WeatherClass(
    val current: CurrentTemp,
    val forecast: Forecast,
    val location: Location
)