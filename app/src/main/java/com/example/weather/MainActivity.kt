package com.example.weather

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.weather.adapter.TemperatureAdapter
import com.example.weather.networking.WeatherService
import com.example.weather.repo.WeatherRepository
import com.example.weather.viewModels.WeatherViewModel
import com.example.weather.viewModels.WeatherViewModelFactory
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter


class MainActivity : AppCompatActivity() {

    private var cityName : String = "Sydney"
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
        val viewModel = ViewModelProvider(this, WeatherViewModelFactory(repository))
            .get(WeatherViewModel::class.java)


        viewModel.getCurrTemp(API_KEY,cityName,10)


        /// TO-DO
        /// Fetch data from intent
        /// Change weather data



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

        val addButton = findViewById<FloatingActionButton>(R.id.floatingActionButton)

        templist = ArrayList()



        viewModel.weather.observe(this , Observer {

            if(it != null){
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
                    templist.add(Join(i.day.avgtemp_c, getDay(i.date), i.day.condition.text))
                }
                adapter.getAllList(templist)
            } else {
                val mainView = findViewById<LinearLayout>(R.id.mainView)
                mainView.visibility = View.GONE
                Toast.makeText(this,"Error while fetching data", Toast.LENGTH_LONG).show()
            }
        })

        addButton.setOnClickListener {
            val intent = Intent(this, SearchActivity::class.java).apply {
                putExtra("CITY", city.text)
            }
            startActivity(intent)
        }

    }

    @SuppressLint("NewApi")
    fun getDay(date: String) : String {
        val inFormat = SimpleDateFormat("yyyy-MM-dd")
        val newDate = inFormat.parse(date)
        val outFormat = SimpleDateFormat("EEEE")
        if(date == LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))){
            return "Today"
        }
        return outFormat.format(newDate)
    }

}
