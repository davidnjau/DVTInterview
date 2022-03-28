package com.example.dvt.roomdatabase

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "today_weather_data")
data class TodayWeatherInfo(

        val lat: Double,
        val lon: Double,
        val name: String,
        val date: String,
        val time: String,

        val temp: Double,
        val feels_like: Double,
        val temp_min: Double,
        val temp_max: Double,

        val pressure: Double,
        val humidity: Double,
        val visibility: Double,

        val wind_speed: Double,
        val wind_degrees: Double,

        val sunrise: String,
        val sunset:String
){
        @PrimaryKey(autoGenerate = true)
        var id: Int? = null
}

@Entity(tableName = "weather_forecast_data")
data class WeatherForecastInfo(

        val temp: Double,
        val feels_like: Double,
        val temp_min: Double,
        val temp_max: Double,

        val pressure: Double,
        val humidity: Double,
        val visibility: Double,

        val wind_speed: Double,
        val wind_degrees: Double,

        val date: String,
        val time: String,

){
        @PrimaryKey(autoGenerate = true)
        var id: Int? = null
}