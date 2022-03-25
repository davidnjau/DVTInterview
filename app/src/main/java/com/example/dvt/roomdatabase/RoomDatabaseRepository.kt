package com.example.dvt.roomdatabase

import android.util.Log
import com.example.dvt.helper_class.FormatterHelper

class RoomDatabaseRepository(private val weatherDao: WeatherDao) {

    // Room executes all queries on a separate thread.
    // Observed LiveData will notify the observer when the data has changed.


//    fun getTodayWeather(taskId: String): TodayWeatherInfo = runBlocking{
//        weatherDao.getTodayWeather("", 9.9, 8.8)
//    }

    fun addTodayWeather() {

        val formatterHelper = FormatterHelper()

        val todayDate = formatterHelper.getTodayDate()
        Log.e("----- ", "-----")
        print(todayDate)

    }
}