package com.example.dvt.roomdatabase

import com.example.dvt.helper_class.FormatterHelper
import com.example.dvt.helper_class.TodayWeatherData

class RoomDatabaseRepository(private val weatherDao: WeatherDao) {

    private val formatterHelper = FormatterHelper()

    // Room executes all queries on a separate thread.
    // Observed LiveData will notify the observer when the data has changed.


//    fun getTodayWeather(taskId: String): TodayWeatherInfo = runBlocking{
//        weatherDao.getTodayWeather("", 9.9, 8.8)
//    }

    private fun checkDateLatLon(date: String, lat: Double, lon: Double):Boolean{
        return weatherDao.checkTodayData(date, lat, lon)
    }

    private fun getResponseData(responseBody: TodayWeatherData): TodayWeatherInfo {

        val todayDateTime = formatterHelper.getTodayDateTime()
        val todayDate = formatterHelper.getDate(todayDateTime)
        val todayTime = formatterHelper.getTime(todayDateTime)

        val lat = responseBody.coord.lat
        val lon = responseBody.coord.lon

        val name = responseBody.name
        val cod = responseBody.cod


        val dt = responseBody.dt
        val visibility = responseBody.visibility
        val base = responseBody.base

        val type = responseBody.sys.type
        val id = responseBody.sys.id
        val country = responseBody.sys.country
        val sunrise = responseBody.sys.sunrise
        val sunset = responseBody.sys.sunset

        val main = responseBody.main
        val temp = main.temp
        val feels_like = main.feels_like
        val temp_min = main.temp_min
        val temp_max = main.temp_max
        val pressure = main.pressure
        val humidity = main.humidity

        val clouds = responseBody.clouds
        val all = clouds.all

        val wind = responseBody.wind
        val speed = wind.speed
        val deg = wind.deg


        val weatherList = responseBody.weather
        for (weatherItem in weatherList) {

            val id = weatherItem.id
            val weatherMain = weatherItem.main
            val description = weatherItem.description
            val icon = weatherItem.icon

        }

        val sunriseDate = formatterHelper.getDateFromMilli(sunrise)
        val sunsetTime = formatterHelper.getDateFromMilli(sunset)

        val sunriseDateTime = formatterHelper.getDateTime(sunriseDate)
        val sunsetDateTime = formatterHelper.getDateTime(sunsetTime)


        return TodayWeatherInfo(
            lat, lon, name, todayDate,
            todayTime, temp, feels_like, temp_min, temp_max, pressure,
            humidity, visibility, speed, deg, sunriseDateTime, sunsetDateTime
        )


    }

    suspend fun addTodayWeather(todayWeatherData: TodayWeatherData) {

        val lat = 9.2
        val lon = 8.0

        val todayDateTime = formatterHelper.getTodayDateTime()
        val todayDate = formatterHelper.getDate(todayDateTime)

        val todayWeatherInfo = getResponseData(todayWeatherData)

        //Check if date exists with lat and lon
        val isSaved = checkDateLatLon(todayDate, lat, lon)
        if (!isSaved){
            //Does not exist so save it
            weatherDao.addTodayWeather(todayWeatherInfo)
        }


    }
}