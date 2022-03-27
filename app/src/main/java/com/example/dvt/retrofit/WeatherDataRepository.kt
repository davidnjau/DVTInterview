package com.example.dvt.retrofit

import android.app.Application
import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.example.dvt.R
import com.example.dvt.helper_class.FormatterHelper
import com.example.dvt.helper_class.TodayWeatherData
import com.example.dvt.helper_class.WeatherForecast
import com.example.dvt.roomdatabase.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class WeatherDataRepository() {


    companion object {

        //Get the weather Forecast for the next few days
        fun getWeatherForecast(): MutableLiveData<WeatherForecast> {

            val weatherForecastLiveData: MutableLiveData<WeatherForecast> = MutableLiveData<WeatherForecast>()

            CoroutineScope(Dispatchers.Default).launch {

                launch(Dispatchers.IO) {

                    val apiInterface = ApiInterface.create()
                    var response = apiInterface.getWeatherForecast(-1.2240538, 36.6810324, "2567e372cd849bf839c9fe854da69a5c").execute()
                    
                    withContext(Dispatchers.Default) {
                        response.let {
                            
                            if (response.isSuccessful) {

                                val responseBody = response.body()

                                if (responseBody != null){

                                    val cod = responseBody.cod
                                    val cnt = responseBody.cnt
                                    val list = responseBody.list
                                    for (item in list){

                                        val dt  = item.dt
                                        val visibility  = item.visibility
                                        val dt_txt  = item.dt_txt

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





                                    }


                                    //Save data to Room database


//                                weatherForecastLiveData.postValue(response.body() )

                                }


                            }

                        }
                    }
                }

            }
            return weatherForecastLiveData
        }

        //Get today's weather data
        fun getTodayWeather(): MutableLiveData<TodayWeatherData> {

            val context = Application().applicationContext
            val formatterHelper = FormatterHelper()

            val todayWeatherLiveData: MutableLiveData<TodayWeatherData> = MutableLiveData<TodayWeatherData>()

            CoroutineScope(Dispatchers.Default).launch {

                launch(Dispatchers.IO) {

                    val latitudeKey = context.getString(R.string.latitude)
                    val longitudeKey = context.getString(R.string.longitude)

                    val latitude = formatterHelper.retrieveSharedPreference(context, latitudeKey)
                    val longitude = formatterHelper.retrieveSharedPreference(context, longitudeKey)

                    if (latitude != null && longitude != null){

                        val apiInterface = ApiInterface.create()
                        var response = apiInterface.getTodayWeather(latitude.toDouble(),
                            longitude.toDouble(),
                            "2567e372cd849bf839c9fe854da69a5c").execute()

                        withContext(Dispatchers.Default) {
                            response.let {

                                if (response.isSuccessful) {
                                    todayWeatherLiveData.postValue(response.body() )
                                }

                            }
                        }

                    }


                }

            }
            return todayWeatherLiveData
        }

    }

}