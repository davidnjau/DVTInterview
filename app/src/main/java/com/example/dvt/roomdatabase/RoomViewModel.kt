package com.example.dvt.roomdatabase

import android.app.Application
import androidx.lifecycle.AndroidViewModel


class RoomViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: RoomDatabaseRepository

    init {
        val weatherDao = WeatherRoomDatabase.getDatabase(application).weatherDao()
        repository = RoomDatabaseRepository(weatherDao)

    }

    fun getDevControlLiveData(){

        return repository.addTodayWeather()

    }


}