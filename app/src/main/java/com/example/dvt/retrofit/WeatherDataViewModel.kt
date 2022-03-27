package com.example.dvt.retrofit

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.dvt.helper_class.TodayWeatherData
import com.example.dvt.helper_class.WeatherForecast
import java.util.ArrayList

class WeatherDataViewModel: ViewModel() {



    private var forecastLiveData= MutableLiveData<WeatherForecast>()
    private var todayLiveData= MutableLiveData<TodayWeatherData>()

    fun getWeatherForecast(): LiveData<WeatherForecast> { return forecastLiveData }
    fun getTodayWeather(): LiveData<TodayWeatherData> { return todayLiveData }



    init {

        try {
            forecastLiveData= WeatherDataRepository.getWeatherForecast()
            todayLiveData= WeatherDataRepository.getTodayWeather()
        }catch (e: Exception) {
            print(e)
        }

    }



}