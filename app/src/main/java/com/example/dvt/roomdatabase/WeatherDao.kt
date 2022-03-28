package com.example.dvt.roomdatabase

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface WeatherDao {

    @Insert
    suspend fun addTodayWeather(todayWeatherInfo: TodayWeatherInfo)

    @Query("SELECT EXISTS (SELECT 1 FROM today_weather_data WHERE date = :date AND lat =:lat AND lon =:lon)")
    fun checkTodayData(date:String, lat:Double, lon:Double): Boolean

    @Insert
    suspend fun addForecastWeather(weatherForecastInfo:WeatherForecastInfo)

    @Query("SELECT EXISTS (SELECT 1 FROM weather_forecast_data WHERE date = :date AND time = :time)")
    fun checkForecastData(date: String, time: String):Boolean

//    @Query("SELECT * from today_weather_data WHERE date = :date AND lat =:lat AND lon =:lon")
//    suspend fun getTodayWeather(date:String, lat:Double, lon:Double): TodayWeatherInfo




}