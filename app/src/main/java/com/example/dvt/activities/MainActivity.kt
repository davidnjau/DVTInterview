package com.example.dvt.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.example.dvt.R
import com.example.dvt.helper_class.FormatterHelper
import com.example.dvt.helper_class.LocationFinderGPSNLP
import com.example.dvt.helper_class.TodayWeatherData
import com.example.dvt.helper_class.WeatherForecast
import com.example.dvt.retrofit.WeatherDataViewModel
import com.example.dvt.roomdatabase.RoomViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView


class MainActivity : AppCompatActivity() {


    private val weatherDataViewModel: WeatherDataViewModel by viewModels()
    private val formatterHelper = FormatterHelper()
    private lateinit var roomViewModel: RoomViewModel

    private lateinit var bottom_Nav_navigation: BottomNavigationView
    private var finder: LocationFinderGPSNLP? = null

    private var longitude = 0.000
    private var latitude = 0.000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        finder = LocationFinderGPSNLP(this)

        bottom_Nav_navigation = findViewById(R.id.bottom_Nav_navigation)

        bottom_Nav_navigation.setOnNavigationItemSelectedListener(navigationItemSelectedListener)


        roomViewModel = RoomViewModel(this.application)

        //Get Livedata for the forecast
        val weatherForecastObserver= Observer<WeatherForecast> { responseBody ->
            //set data in UI
            roomViewModel.addForecastData(this, responseBody)
        }
        weatherDataViewModel.getWeatherForecast().observe(this,weatherForecastObserver)


        //Get Livedata from Today's endpoint
        val todayWeatherObserver= Observer<TodayWeatherData> { responseBody->
            //set data in UI
            roomViewModel.addTodayWeather(this, responseBody)
        }
        weatherDataViewModel.getTodayWeather().observe(this,todayWeatherObserver)

    }

    private var navigationItemSelectedListener =
        BottomNavigationView.OnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_location -> {
//                    openDrawer()
                    getCurrentLocation()

                }
                R.id.navigation_home -> {
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    return@OnNavigationItemSelectedListener true
                }

                R.id.navigation_profile -> {
//                    val intent = Intent(this, Profile::class.java)
//                    startActivity(intent)
                    return@OnNavigationItemSelectedListener true
                }
            }
            false
        }

    override fun onStart() {
        super.onStart()

        getCurrentLocation()

    }

    private fun getCurrentLocation(): Boolean {
        var isLocation = false
        if (finder!!.canGetLocation()) {
            latitude = finder!!.latitude
            longitude = finder!!.longitude
            if (latitude != 0.000 && longitude != 0.000) {
                isLocation = true

                //Add these to the shared preference
                val latitudeKey = getString(R.string.latitude)
                val longitudeKey = getString(R.string.longitude)

                val lat = latitude.toString()
                val lon = longitude.toString()

                formatterHelper.saveSharedPreference(this, latitudeKey, lat)
                formatterHelper.saveSharedPreference(this, longitudeKey, lon)

            }
        }
        return isLocation
    }

}