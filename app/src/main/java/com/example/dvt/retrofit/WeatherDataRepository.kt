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

            val context = Application().applicationContext
            val formatterHelper = FormatterHelper()

            val weatherForecastLiveData: MutableLiveData<WeatherForecast> = MutableLiveData<WeatherForecast>()

            CoroutineScope(Dispatchers.Default).launch {

                launch(Dispatchers.IO) {

                    val latitudeKey = context.getString(R.string.latitude)
                    val longitudeKey = context.getString(R.string.longitude)
                    val apiDataKey = context.getString(R.string.api_key)

                    val latitude = formatterHelper.retrieveSharedPreference(context, latitudeKey)
                    val longitude = formatterHelper.retrieveSharedPreference(context, longitudeKey)
                    val apiKey = formatterHelper.retrieveSharedPreference(context, apiDataKey)

                    if (latitude != null && longitude != null && apiKey != null){

                        val apiInterface = ApiInterface.create()
                        var response = apiInterface.getWeatherForecast(
                            latitude.toDouble(),
                            longitude.toDouble(),
                            apiKey).execute()

                        withContext(Dispatchers.Default) {
                            response.let {

                                if (response.isSuccessful) {

                                    weatherForecastLiveData.postValue(response.body() )

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
                    val apiDataKey = context.getString(R.string.api_key)

                    val latitude = formatterHelper.retrieveSharedPreference(context, latitudeKey)
                    val longitude = formatterHelper.retrieveSharedPreference(context, longitudeKey)
                    val apiKey = formatterHelper.retrieveSharedPreference(context, apiDataKey)

                    if (latitude != null && longitude != null && apiKey != null){

                        val apiInterface = ApiInterface.create()
                        var response = apiInterface.getTodayWeather(
                            latitude.toDouble(),
                            longitude.toDouble(),
                            apiKey).execute()

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