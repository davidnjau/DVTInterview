package com.example.dvt.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.example.dvt.R
import com.example.dvt.roomdatabase.RoomViewModel

class FavouriteLocationDetails : AppCompatActivity() {

    private lateinit var roomViewModel: RoomViewModel

    private lateinit var tvTemp: TextView
    private lateinit var tvWeather: TextView
    private lateinit var tvLocation: TextView
    private lateinit var tvSunrise: TextView
    private lateinit var tvSunset: TextView
    private lateinit var tvTempFeelsLike: TextView

    private lateinit var tvTempMin: TextView
    private lateinit var tvTempMax: TextView
    private lateinit var tvPressure: TextView
    private lateinit var tvHumidity: TextView
    private lateinit var tvWindSpeed: TextView
    private lateinit var tvWindDegrees: TextView
    private lateinit var tvUpdatedOn: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favourite_location_details)

        roomViewModel = RoomViewModel(this.application)

        tvTemp = findViewById(R.id.tvTemp)
        tvWeather = findViewById(R.id.tvWeather)
        tvLocation = findViewById(R.id.tvLocation)
        tvSunrise = findViewById(R.id.tvSunrise)
        tvSunset = findViewById(R.id.tvSunset)
        tvTempFeelsLike = findViewById(R.id.tvTempFeelsLike)
        tvTempMin = findViewById(R.id.tvTempMin)
        tvTempMax = findViewById(R.id.tvTempMax)
        tvPressure = findViewById(R.id.tvPressure)
        tvHumidity = findViewById(R.id.tvHumidity)
        tvWindSpeed = findViewById(R.id.tvWindSpeed)
        tvWindDegrees = findViewById(R.id.tvWindDegrees)
        tvUpdatedOn = findViewById(R.id.tvUpdatedOn)


    }

    override fun onStart() {
        super.onStart()

        getFavLocationDetails()
    }

    private fun getFavLocationDetails() {

        val favouriteLocationDetails = roomViewModel.getFavLocationsDetails(this)
        if (favouriteLocationDetails != null){

            tvTemp.text = favouriteLocationDetails.temp.toString()
            tvWeather.text = favouriteLocationDetails.weatherDescription
            tvLocation.text = favouriteLocationDetails.name
            tvSunrise.text = favouriteLocationDetails.sunrise
            tvSunset.text = favouriteLocationDetails.sunset
            tvTempFeelsLike.text = favouriteLocationDetails.feels_like.toString()
            tvTempMin.text = favouriteLocationDetails.temp_min.toString()
            tvTempMax.text = favouriteLocationDetails.temp_max.toString()
            tvPressure.text = favouriteLocationDetails.pressure.toString()
            tvHumidity.text = favouriteLocationDetails.humidity.toString()
            tvWindSpeed.text = favouriteLocationDetails.wind_speed.toString()
            tvWindDegrees.text = favouriteLocationDetails.wind_degrees.toString()
            tvUpdatedOn.text = favouriteLocationDetails.updatedOn


        }


    }
}