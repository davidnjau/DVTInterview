package com.example.dvt.retrofit

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.dvt.helper_class.TodayWeatherData
import com.example.dvt.helper_class.WeatherForecast

class WeatherDataViewModel (application: Application) : AndroidViewModel(application) {



    private var forecastLiveData= MutableLiveData<WeatherForecast>()
    private var todayLiveData= MutableLiveData<TodayWeatherData>()

    fun getWeatherForecast(): LiveData<WeatherForecast> { return forecastLiveData }
    fun getTodayWeather(): LiveData<TodayWeatherData> { return todayLiveData }

    init {

        try {
            forecastLiveData= WeatherDataRepository.getWeatherForecast(getApplication<Application>())
            todayLiveData= WeatherDataRepository.getTodayWeather(getApplication<Application>())
        }catch (e: Exception) {
            Log.e("------",e.toString())
            print(e)
        }

    }



}