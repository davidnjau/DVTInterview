package com.example.dvt.roomdatabase

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.dvt.helper_class.TodayWeatherData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class RoomViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: RoomDatabaseRepository

    init {
        val weatherDao = WeatherRoomDatabase.getDatabase(application).weatherDao()
        repository = RoomDatabaseRepository(weatherDao)

    }


    fun addTodayWeather(todayWeatherData: TodayWeatherData){

        CoroutineScope(Dispatchers.IO).launch {

            repository.addTodayWeather(todayWeatherData)

        }

    }


}