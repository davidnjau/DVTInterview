package com.example.dvt.retrofit

import android.app.Application
import android.content.Context
import android.util.Log
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
        fun getWeatherForecast(application: Application): MutableLiveData<WeatherForecast> {
            Log.e("----- ", "2")
            val context = application.applicationContext
            val formatterHelper = FormatterHelper()
            val roomViewModel = RoomViewModel(application)

            val weatherForecastLiveData: MutableLiveData<WeatherForecast> = MutableLiveData<WeatherForecast>()

            CoroutineScope(Dispatchers.Default).launch {

                launch(Dispatchers.IO) {

                    val latitudeKey = context.getString(R.string.latitude)
                    val longitudeKey = context.getString(R.string.longitude)
                    val apiDataKey = context.getString(R.string.api_key)

                    val latitude = formatterHelper.retrieveSharedPreference(context, latitudeKey)
                    val longitude = formatterHelper.retrieveSharedPreference(context, longitudeKey)
                    Log.e("-----2 ", latitude.toString())

                    if (latitude != null && longitude != null){

                        val apiInterface = ApiInterface.create()
                        var response = apiInterface.getWeatherForecast(
                            latitude.toDouble(),
                            longitude.toDouble(),
                            apiDataKey).execute()

                        withContext(Dispatchers.Default) {
                            response.let {

                                if (response.isSuccessful) {

                                    weatherForecastLiveData.postValue(response.body() )
                                    response.body()
                                        ?.let { it1 -> roomViewModel.addForecastData(context, it1) }
                                }

                                Log.e("-----1 ", response.toString())


                            }
                        }

                    }else{
                        CoroutineScope(Dispatchers.Main).launch {
                            formatterHelper.getCurrentLocation(context)
                        }
                    }


                }

            }
            return weatherForecastLiveData
        }

        //Get today's weather data
        fun getTodayWeather(application: Application): MutableLiveData<TodayWeatherData> {
            Log.e("----- ", "3")
            val context = application.applicationContext
            val formatterHelper = FormatterHelper()
            val roomViewModel = RoomViewModel(application)
            val todayWeatherLiveData: MutableLiveData<TodayWeatherData> = MutableLiveData<TodayWeatherData>()

            CoroutineScope(Dispatchers.Default).launch {

                launch(Dispatchers.IO) {

                    val latitudeKey = context.getString(R.string.latitude)
                    val longitudeKey = context.getString(R.string.longitude)
                    val apiDataKey = context.getString(R.string.api_key)

                    val latitude = formatterHelper.retrieveSharedPreference(context, latitudeKey)
                    val longitude = formatterHelper.retrieveSharedPreference(context, longitudeKey)

                    if (latitude != null && longitude != null){

                        val apiInterface = ApiInterface.create()
                        var response = apiInterface.getTodayWeather(
                            latitude.toDouble(),
                            longitude.toDouble(),
                            apiDataKey).execute()

                        withContext(Dispatchers.Default) {
                            response.let {

                                if (response.isSuccessful) {

                                    todayWeatherLiveData.postValue(response.body() )

                                    response.body()
                                        ?.let { it1 -> roomViewModel.addTodayWeather(context, it1) }
                                }

                            }
                        }

                    }else{
                        CoroutineScope(Dispatchers.Main).launch {
                            formatterHelper.getCurrentLocation(context)
                        }
                    }


                }

            }
            return todayWeatherLiveData
        }

    }

}