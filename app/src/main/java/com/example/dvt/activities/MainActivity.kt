package com.example.dvt.activities

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.LocationManager
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.lifecycle.Observer
import com.example.dvt.R
import com.example.dvt.helper_class.*
import com.example.dvt.retrofit.WeatherDataViewModel
import com.example.dvt.roomdatabase.RoomViewModel


class MainActivity : AppCompatActivity() {

    private val REQUEST_LOCATION = 1
    var locationManager: LocationManager? = null

    private val weatherDataViewModel: WeatherDataViewModel by viewModels()
    private val formatterHelper = FormatterHelper()
    private lateinit var roomViewModel: RoomViewModel
    var latitude = ""
    var longitude = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        roomViewModel = RoomViewModel(this.application)

        ActivityCompat.requestPermissions(this@MainActivity, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), REQUEST_LOCATION)

        //Get Livedata for the forecast
        val weatherForecastObserver= Observer<WeatherForecast> { weatherForecast->
            //set data in UI
            Log.e("----- " , weatherForecast.toString())

        }
        weatherDataViewModel.getWeatherForecast().observe(this,weatherForecastObserver)


        //Get Livedata from Today's endpoint
        val todayWeatherObserver= Observer<TodayWeatherData> { responseBody->
            //set data in UI
            roomViewModel.addTodayWeather(this, responseBody)

        }
        weatherDataViewModel.getTodayWeather().observe(this,todayWeatherObserver)

    }

    override fun onStart() {
        super.onStart()

        getCurrentUserLocation()
    }

    private fun getCurrentUserLocation() {

        locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager?
        if (!locationManager!!.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            onGPS()
        } else {
            getLocation()
        }

    }

    private fun onGPS() {
        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
        builder.setMessage("Enable GPS").setCancelable(false).setPositiveButton("Yes") {
                _, _ -> startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS))
        }.setNegativeButton("No") {
                dialog, _ -> dialog.cancel()
        }
        val alertDialog: AlertDialog = builder.create()
        alertDialog.show()
    }

    private fun getLocation() {
        if (ActivityCompat.checkSelfPermission(
                this@MainActivity, Manifest.permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this@MainActivity, Manifest.permission.ACCESS_COARSE_LOCATION)
            != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                REQUEST_LOCATION)
        } else {
            val locationGPS = locationManager?.getLastKnownLocation(LocationManager.GPS_PROVIDER)
            if (locationGPS != null) {
                val lat: Double = locationGPS.latitude
                val longi: Double = locationGPS.longitude

                latitude = lat.toString()
                longitude = longi.toString()

                val latitudeKey = getString(R.string.latitude)
                val longitudeKey = getString(R.string.longitude)

                formatterHelper.saveSharedPreference(this , latitudeKey, latitude)
                formatterHelper.saveSharedPreference(this, longitudeKey, longitude)

            } else {
                Toast.makeText(this, "Unable to find location.", Toast.LENGTH_SHORT).show()
            }
        }
    }

}