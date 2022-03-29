package com.example.dvt.roomdatabase

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import com.example.dvt.helper_class.TodayWeatherData
import com.example.dvt.helper_class.WeatherForecast
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking


class RoomViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: RoomDatabaseRepository

    init {
        val weatherDao = WeatherRoomDatabase.getDatabase(application).weatherDao()
        repository = RoomDatabaseRepository(weatherDao)

    }


    fun addTodayWeather(context: Context, todayWeatherData: TodayWeatherData){
        CoroutineScope(Dispatchers.IO).launch {
            repository.addTodayWeather(context, todayWeatherData)
        }
    }

    fun addForecastData(context: Context, todayWeatherData: WeatherForecast){
        CoroutineScope(Dispatchers.IO).launch {
            repository.addForecastData(context, todayWeatherData)
        }
    }

    fun getTodayWeather()= runBlocking {

        repository.getTodayWeather()

    }
    fun getForecastWeatherList()= runBlocking {

        repository.getForecastWeatherList()

    }
    fun getFavWeatherList()= runBlocking {

        repository.getFavWeatherList()

    }
    fun getFavLocationsDetails(context: Context)= runBlocking {

        repository.getFavLocationsDetails(context)

    }
    fun addFavWeather(context: Context){
        CoroutineScope(Dispatchers.IO).launch {
            repository.addFavWeather(context)
        }
    }
    fun checkFavWeather(context: Context)= runBlocking {
        repository.checkFavWeather(context)
    }

}