package com.example.dvt.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dvt.R
import com.example.dvt.helper_class.FavLocationAdapter
import com.example.dvt.helper_class.ForecastListingAdapter
import com.example.dvt.helper_class.FormatterHelper
import com.example.dvt.roomdatabase.RoomViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FavouriteLocation : AppCompatActivity() {

    private lateinit var bottom_Nav_navigation: BottomNavigationView
    private val formatterHelper = FormatterHelper()
    private lateinit var recyclerView : RecyclerView
    private lateinit var layoutManager: RecyclerView.LayoutManager
    private lateinit var roomViewModel: RoomViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favourite_location)

        bottom_Nav_navigation = findViewById(R.id.bottom_Nav_navigation)

        bottom_Nav_navigation.setOnNavigationItemSelectedListener(navigationItemSelectedListener)

        roomViewModel = RoomViewModel(this.application)

        recyclerView = findViewById(R.id.recyclerView)
        layoutManager = LinearLayoutManager(
            this,
            LinearLayoutManager.VERTICAL,
            false
        )
        recyclerView.layoutManager = layoutManager
        recyclerView.setHasFixedSize(true)
    }

    override fun onStart() {
        super.onStart()

        getFavLocations()
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

    private fun getFavLocations(){
        CoroutineScope(Dispatchers.IO).launch {

            val favouriteLocationList = roomViewModel.getFavWeatherList()

            CoroutineScope(Dispatchers.Main).launch {
                val patientsListingAdapter = FavLocationAdapter(
                    favouriteLocationList,this@FavouriteLocation)
                recyclerView.adapter = patientsListingAdapter
            }

        }

    }
}