package com.example.weather.modals

data class CurrentTemp(
    val cloud: Int,
    val condition: Condition,
    val humidity: Int,
    val last_updated: String,
    val temp_c: Double,
    val temp_f: Double,
    val uv: Double,
    val wind_kph: Double
    )