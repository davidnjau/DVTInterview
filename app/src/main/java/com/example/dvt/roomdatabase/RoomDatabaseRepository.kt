package com.example.dvt.roomdatabase

import android.content.Context
import android.util.Log
import androidx.core.app.ActivityCompat
import com.example.dvt.R
import com.example.dvt.helper_class.FormatterHelper
import com.example.dvt.helper_class.TodayWeatherData
import com.example.dvt.helper_class.WeatherForecast

class RoomDatabaseRepository(private val weatherDao: WeatherDao) {

    private val formatterHelper = FormatterHelper()

    // Room executes all queries on a separate thread.
    // Observed LiveData will notify the observer when the data has changed.

    private fun checkTodayDateLatLon(date: String, lat: Double, lon: Double):Boolean{
        return weatherDao.checkTodayData(date, lat, lon)
    }

    private fun getTodayResponseData(responseBody: TodayWeatherData): TodayWeatherInfo {

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

    suspend fun addTodayWeather(context:Context, todayWeatherData: TodayWeatherData) {

        val latitudeKey = context.getString(R.string.latitude)
        val longitudeKey = context.getString(R.string.longitude)

        val latitude = formatterHelper.retrieveSharedPreference(context, latitudeKey)
        val longitude = formatterHelper.retrieveSharedPreference(context, longitudeKey)

        if (latitude != null && longitude != null){

            val todayDateTime = formatterHelper.getTodayDateTime()
            val todayDate = formatterHelper.getDate(todayDateTime)

            val todayWeatherInfo = getTodayResponseData(todayWeatherData)

            //Check if date exists with lat and lon
            val isSaved = checkTodayDateLatLon(todayDate, latitude.toDouble(), longitude.toDouble())
            if (!isSaved){
                //Does not exist so save it
                weatherDao.addTodayWeather(todayWeatherInfo)
            }
        }

        Log.e("---- " , latitude.toString())

    }


    private fun checkForecastLatLon(date: String, time:String): Boolean{
        return weatherDao.checkForecastData(date, time)
    }

    suspend fun addForecastData(context: Context, weatherForecast: WeatherForecast){

        val forecastList = getForecastData(weatherForecast)
        for(item in forecastList){

            val date = item.date
            val time = item.time

            val isForecast = checkForecastLatLon(date, time)
            if(!isForecast){

                val visibility  = item.visibility

                val temp = item.temp
                val feels_like = item.feels_like
                val temp_min = item.temp_min
                val temp_max = item.temp_max
                val pressure = item.pressure
                val humidity = item.humidity

                val speed = item.wind_speed
                val deg = item.wind_degrees

                val forecastInfo = WeatherForecastInfo(
                    temp, feels_like, temp_min, temp_max, pressure, humidity,
                    visibility,speed,deg,date, time)

                weatherDao.addForecastWeather(forecastInfo)

            }

        }


    }

    private fun getForecastData(responseBody: WeatherForecast):List<WeatherForecastInfo>{

        val forecastList = ArrayList<WeatherForecastInfo>()

        val cod = responseBody.cod
        val cnt = responseBody.cnt
        val list = responseBody.list
        for (item in list){

            //Use this as the id
            val dt_txt  = item.dt_txt

            val dt  = item.dt
            val visibility  = item.visibility

            val main  = item.main
            val temp = main.temp
            val feels_like = main.feels_like
            val temp_min = main.temp_min
            val temp_max = main.temp_max
            val pressure = main.pressure
            val humidity = main.humidity

            val clouds  = item.clouds
            val all = clouds.all

            val wind  = item.wind
            val speed = wind.speed
            val deg = wind.deg


            val weatherList  = item.weather
            for (weatherItem in weatherList){

                val id = weatherItem.id
                val weatherMain = weatherItem.main
                val description = weatherItem.description
                val icon = weatherItem.icon

            }

            val date = formatterHelper.getDate(dt_txt)
            val time = formatterHelper.getTime(dt_txt)

            val weatherForecastInfo = WeatherForecastInfo(
                temp, feels_like, temp_min, temp_max, pressure, humidity,
                visibility,speed, deg, date, time)

            forecastList.add(weatherForecastInfo)

        }

        return forecastList


    }

}