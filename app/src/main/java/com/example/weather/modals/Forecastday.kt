package com.example.weather.modals

data class Forecastday(
    val astro: Astro,
    val date: String,
    val day: DailyTemp,
)