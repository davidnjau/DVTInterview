package com.example.dvt.roomdatabase

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface WeatherDao {

    @Insert
    suspend fun addTodayWeather(todayWeatherInfo: TodayWeatherInfo)

//    @Query("SELECT EXISTS (SELECT 1 FROM today_weather_data WHERE date = :date AND lat =:lat AND lon =:lon)")
//    fun checkTodayData(date:String, lat:Double, lon:Double): Boolean

//    @Query("SELECT * from today_weather_data WHERE date = :date AND lat =:lat AND lon =:lon")
//    suspend fun getTodayWeather(date:String, lat:Double, lon:Double): TodayWeatherInfo




}