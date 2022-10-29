package com.example.weather

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.weather.adapter.TemperatureAdapter
import com.example.weather.networking.WeatherService
import com.example.weather.repo.WeatherRepository
import com.example.weather.viewModels.WeatherViewModel
import com.example.weather.viewModels.WeatherViewModelFactory
import kotlin.collections.ArrayList


const val API_KEY = "f5f79c7077e3422fb67153224210510"


class MainActivity : AppCompatActivity() {

    private lateinit var cityName : String

    private lateinit var adapter: TemperatureAdapter

    private lateinit var templist : ArrayList<Join>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val dailyRecycler = findViewById<RecyclerView>(R.id.dailyRecycler)
        adapter = TemperatureAdapter()
        val layoutManager = LinearLayoutManager(this)
        dailyRecycler.layoutManager = layoutManager
        dailyRecycler.adapter = adapter

        val weatherInstance = WeatherService.getInstance()
        val repository = WeatherRepository(weatherInstance)
        val viewModel = ViewModelProvider(this, WeatherViewModelFactory(repository)).get(WeatherViewModel::class.java)


        cityName = "Lahore"


        viewModel.getCurrTemp(API_KEY,cityName,10)


        val city = findViewById<TextView>(R.id.city)
        val currentTemp = findViewById<TextView>(R.id.currentTemp)
        val top = findViewById<ImageView>(R.id.top)
        val bottom = findViewById<ImageView>(R.id.bottom)
        val accessories = findViewById<ImageView>(R.id.accessories)
        val description = findViewById<TextView>(R.id.description)
        val lastUpdated = findViewById<TextView>(R.id.lastUpdated)
        val humidity = findViewById<TextView>(R.id.humidity)
        val wind = findViewById<TextView>(R.id.wind)
        val sunset = findViewById<TextView>(R.id.sunset)
        val sunrise = findViewById<TextView>(R.id.sunrise)


        templist = ArrayList()
        viewModel.weather.observe(this , Observer {
            city.text = it.location.name
            currentTemp.text = it.current.temp_c.toString()
            if(it.current.temp_c > 15.0){
                top.setImageResource(R.drawable.tshirt)
                bottom.setImageResource(R.drawable.shorts)
                accessories.setImageResource(R.drawable.cap)
            } else {
                top.setImageResource(R.drawable.jacket)
                bottom.setImageResource(R.drawable.trousers)
                accessories.setImageResource(R.drawable.scarf)
            }

            description.text = it.current.condition.text
            lastUpdated.text = "Last Updated at ${it.location.localtime}"
            humidity.text = it.current.humidity.toString()



            wind.text = it.current.wind_kph.toString()
            sunset.text = it.forecast.forecastday[0].astro.sunset
            sunrise.text = it.forecast.forecastday[0].astro.sunrise
            for(i in it.forecast.forecastday){
                templist.add(Join(i.day.avgtemp_c, i.date))
            }
            adapter.getAllList(templist)
        })

    }
}

class Join(val temp : Double , val date : String)