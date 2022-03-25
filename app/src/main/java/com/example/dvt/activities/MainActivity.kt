package com.example.dvt.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.example.dvt.R
import com.example.dvt.helper_class.*
import com.example.dvt.retrofit.WeatherDataViewModel
import com.example.dvt.roomdatabase.RoomViewModel
import com.example.dvt.roomdatabase.TodayWeatherInfo

class MainActivity : AppCompatActivity() {

    private val weatherDataViewModel: WeatherDataViewModel by viewModels()
    private val formatterHelper = FormatterHelper()
    private lateinit var roomViewModel: RoomViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        roomViewModel = RoomViewModel(this.application)

        //Get Livedata for the forecast
        val weatherForecastObserver= Observer<WeatherForecast> { weatherForecast->
            //set data in UI
            Log.e("----- " , weatherForecast.toString())

        }
        weatherDataViewModel.getWeatherForecast().observe(this,weatherForecastObserver)


        //Get Livedata from Today's endpoint
        val todayWeatherObserver= Observer<TodayWeatherData> { responseBody->
            //set data in UI
            roomViewModel.addTodayWeather(responseBody)

        }
        weatherDataViewModel.getTodayWeather().observe(this,todayWeatherObserver)



    }
}