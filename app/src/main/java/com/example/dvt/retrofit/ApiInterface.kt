package com.example.dvt.retrofit

import com.example.dvt.helper_class.*
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.concurrent.TimeUnit


interface ApiInterface {

    @GET("data/2.5/weather")
    fun getTodayWeather(
        @Query(value = "lat") lat: Double,
        @Query(value = "lon") lon: Double,
        @Query(value = "appid") appid: String) : Call<TodayWeatherData>

    @GET("data/2.5/forecast")
    fun getWeatherForecast(
        @Query(value = "lat") lat: Double,
        @Query(value = "lon") lon: Double,
        @Query(value = "appid") appid: String) : Call<WeatherForecast>

    companion object {

        var BASE_URL = "https://api.openweathermap.org/"

        fun create() : ApiInterface {

            val okHttpClient = OkHttpClient.Builder()
                .connectTimeout(20, TimeUnit.SECONDS)
                .writeTimeout(20, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .build()

            val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .client(okHttpClient)
                .build()
            return retrofit.create(ApiInterface::class.java)

        }
    }

}