package com.example.dvt.retrofit

import androidx.lifecycle.MutableLiveData
import com.example.dvt.helper_class.TodayWeatherData
import com.example.dvt.helper_class.WeatherForecast
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class WeatherDataRepository {

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

                                weatherForecastLiveData.postValue(response.body() )
                            }

                        }
                    }
                }

            }
            return weatherForecastLiveData
        }

        //Get today's weather data
        fun getTodayWeather(): MutableLiveData<TodayWeatherData> {

            val todayWeatherLiveData: MutableLiveData<TodayWeatherData> = MutableLiveData<TodayWeatherData>()

            CoroutineScope(Dispatchers.Default).launch {

                launch(Dispatchers.IO) {

                    val apiInterface = ApiInterface.create()
                    var response = apiInterface.getTodayWeather(-1.2240538, 36.6810324, "2567e372cd849bf839c9fe854da69a5c").execute()

                    withContext(Dispatchers.Default) {
                        response.let {

                            if (response.isSuccessful) {

                                todayWeatherLiveData.postValue(response.body() )
                            }

                        }
                    }
                }

            }
            return todayWeatherLiveData
        }

    }

}