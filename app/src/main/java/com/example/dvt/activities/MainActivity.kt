package com.example.dvt.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.example.dvt.R
import com.example.dvt.helper_class.*
import com.example.dvt.retrofit.WeatherDataViewModel

class MainActivity : AppCompatActivity() {

    private val weatherDataViewModel: WeatherDataViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Get Livedata for the forecast
        val weatherForecastObserver= Observer<WeatherForecast> { weatherForecast->
            //set data in UI
            Log.e("----- " , weatherForecast.toString())

        }
        weatherDataViewModel.getWeatherForecast().observe(this,weatherForecastObserver)


        //Get Livedata from Today's endpoint
        val todayWeatherObserver= Observer<TodayWeatherData> { todayWeather->
            //set data in UI
            Log.e("+++++ " , todayWeather.toString())

        }
        weatherDataViewModel.getTodayWeather().observe(this,todayWeatherObserver)



    }
}