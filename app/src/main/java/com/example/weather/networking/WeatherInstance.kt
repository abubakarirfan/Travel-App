package com.example.weather.networking


import com.example.weather.modals.WeatherClass
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query


interface WeatherInstance {

    @GET("forecast.json")
    suspend fun getCurrentTemp(@Query("key")key : String , @Query("q")query : String , @Query("days")days : Int) : Response<WeatherClass>

}
