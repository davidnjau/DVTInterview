package com.example.dvt.activities

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.RelativeSizeSpan
import android.text.style.SuperscriptSpan
import android.util.Log
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.dvt.R
import com.example.dvt.helper_class.*
import com.example.dvt.retrofit.WeatherDataViewModel
import com.example.dvt.roomdatabase.RoomViewModel
import com.example.dvt.roomdatabase.TodayWeatherInfo
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.*
import java.util.*


class MainActivity : AppCompatActivity() {


    private val weatherDataViewModel: WeatherDataViewModel by viewModels()
    private val formatterHelper = FormatterHelper()
    private lateinit var roomViewModel: RoomViewModel

    private lateinit var bottom_Nav_navigation: BottomNavigationView
    private var finder: LocationFinderGPSNLP? = null

    private lateinit var tvTemp: TextView
    private lateinit var tvLocation: TextView
    private lateinit var tvTempMin: TextView
    private lateinit var tvTempFeelsLike: TextView
    private lateinit var tvTempMax: TextView
    private lateinit var tvWeather: TextView
    private lateinit var tvSunset: TextView
    private lateinit var tvSunrise: TextView
    private lateinit var imgBtnFav: ImageButton
    private var spannableStringBuilder: SpannableStringBuilder? = null

    private lateinit var recyclerView : RecyclerView
    private lateinit var layoutManager: RecyclerView.LayoutManager
    private lateinit var mainBackground: ConstraintLayout

    private lateinit var swipeToRefreshLayout: SwipeRefreshLayout


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        swipeToRefreshLayout = findViewById(R.id.swipeRefresh3)
        swipeToRefreshLayout.setOnRefreshListener {
            Handler().postDelayed({
                swipeToRefreshLayout.isRefreshing = false

                CoroutineScope(Dispatchers.Main).launch {
                    formatterHelper.getCurrentLocation(this@MainActivity)
                    delay(1000)
                }

                CoroutineScope(Dispatchers.IO).launch {
                    weatherDataViewModel.forceFetch()
                    delay(1000)
                }

                getTodayWeather()

            }, 7000)
        }
        swipeToRefreshLayout.setColorSchemeResources(
            R.color.sunny,
            R.color.rainy,
            R.color.cloudy,
            R.color.sunny,
        )

        tvTemp = findViewById(R.id.tvTemp)
        tvLocation = findViewById(R.id.tvLocation)
        tvTempMin = findViewById(R.id.tvTempMin)
        tvTempFeelsLike = findViewById(R.id.tvTempFeelsLike)
        tvTempMax = findViewById(R.id.tvTempMax)
        tvWeather = findViewById(R.id.tvWeather)
        mainBackground = findViewById(R.id.mainBackground)
        tvSunrise = findViewById(R.id.tvSunrise)
        tvSunset = findViewById(R.id.tvSunset)
        imgBtnFav = findViewById(R.id.imgBtnFav)

        recyclerView = findViewById(R.id.recyclerView)
        layoutManager = LinearLayoutManager(
            this,
            LinearLayoutManager.VERTICAL,
            false
        )
        recyclerView.layoutManager = layoutManager
        recyclerView.setHasFixedSize(true)

        finder = LocationFinderGPSNLP(this)

        bottom_Nav_navigation = findViewById(R.id.bottom_Nav_navigation)

        bottom_Nav_navigation.setOnNavigationItemSelectedListener(navigationItemSelectedListener)

        roomViewModel = RoomViewModel(this.application)

        //Get Livedata for the forecast
        val weatherForecastObserver= Observer<WeatherForecast> { responseBody ->
            //set data in UI
            roomViewModel.addForecastData(this, responseBody)
            getTodayWeather()
        }
        weatherDataViewModel.getWeatherForecast().observe(this,weatherForecastObserver)


        //Get Livedata from Today's endpoint
        val todayWeatherObserver= Observer<TodayWeatherData> { responseBody->
            //set data in UI
            roomViewModel.addTodayWeather(this, responseBody)
            getTodayWeather()
        }


        weatherDataViewModel.getTodayWeather().observe(this,todayWeatherObserver)

        imgBtnFav.setOnClickListener {

            addFav()

        }

    }



    private var navigationItemSelectedListener =
        BottomNavigationView.OnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_location -> {
//                    openDrawer()
                    formatterHelper.getCurrentLocation(this)

                }
                R.id.navigation_home -> {
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    return@OnNavigationItemSelectedListener true
                }

//                R.id.navigation_profile -> {
////                    val intent = Intent(this, Profile::class.java)
////                    startActivity(intent)
//                    return@OnNavigationItemSelectedListener true
//                }
                R.id.navigation_fav -> {
                    val intent = Intent(this, FavouriteLocation::class.java)
                    startActivity(intent)
                    return@OnNavigationItemSelectedListener true
                }
                R.id.navigation_map -> {
                    val intent = Intent(this, Maps::class.java)
                    startActivity(intent)
                    return@OnNavigationItemSelectedListener true
                }
            }
            false
        }

    override fun onStart() {
        super.onStart()

        formatterHelper.getCurrentLocation(this)
        getTodayWeather()
    }

    private fun getTodayWeather(){

        val todayWeatherInfo = roomViewModel.getTodayWeather()
        if (todayWeatherInfo != null) {
            val tempMin = todayWeatherInfo.temp_min
            val tempMax = todayWeatherInfo.temp_max
            val tempCurrent = todayWeatherInfo.temp
            val feelsLike = todayWeatherInfo.feels_like
            val weather = todayWeatherInfo.weatherDescription

            val sunrise = todayWeatherInfo.sunrise
            val sunset = todayWeatherInfo.sunset

            val name = todayWeatherInfo.name

            tvTemp.text = tempCurrent.toString()
            tvLocation.text = name
            tvTempMin.text = tempMin.toString()
            tvTempFeelsLike.text = feelsLike.toString()
            tvTempMax.text = tempMax.toString()
            tvWeather.text = weather

            tvSunrise.text = "Sunrise: \n$sunrise"
            tvSunset.text = "Sunset: \n$sunset"
        }

        CoroutineScope(Dispatchers.IO).launch {

            val weatherForecastList = roomViewModel.getForecastWeatherList()

            CoroutineScope(Dispatchers.Main).launch {
                val patientsListingAdapter = ForecastListingAdapter(
                    weatherForecastList,this@MainActivity)
                recyclerView.adapter = patientsListingAdapter
            }

        }

        checkTime()
        checkfav()

    }

    private fun checkTime(){

        val date = Date()
        val cal: Calendar = Calendar.getInstance()
        cal.time = date
        val hour = cal.get(Calendar.HOUR_OF_DAY)

        when (hour) {
            in 6..11 -> {
                mainBackground.setBackgroundResource(R.drawable.morning)
            }
            in 12..16 -> {
                mainBackground.setBackgroundResource(R.drawable.midday)
            }
            in 17..19 -> {
                mainBackground.setBackgroundResource(R.drawable.evening)
            }
            in 20..23 -> {
                mainBackground.setBackgroundResource(R.drawable.night)
            }
        }

    }

    private fun checkfav(){
        val isFav = roomViewModel.checkFavWeather(this@MainActivity)
        if (isFav){
            //Data exists
            imgBtnFav.setBackgroundResource(R.drawable.ic_action_fav_fill_white)

        }else{
            //Data does not exist
            imgBtnFav.setBackgroundResource(R.drawable.ic_action_fav_white)
        }
    }

    private fun addFav(){

        CoroutineScope(Dispatchers.IO).launch {

            val job = Job()
            CoroutineScope(Dispatchers.IO + job).launch {

                roomViewModel.addFavWeather(this@MainActivity)

            }.join()
            delay(1000)
            checkfav()

        }






    }




}