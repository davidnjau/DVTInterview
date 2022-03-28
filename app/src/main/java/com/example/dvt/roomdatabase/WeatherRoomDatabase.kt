package com.example.dvt.roomdatabase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [TodayWeatherInfo::class, WeatherForecastInfo::class, FavouriteLocationInfo::class],
    version = 1,
    exportSchema = true)

public abstract class WeatherRoomDatabase: RoomDatabase() {

    abstract fun weatherDao() : WeatherDao

    companion object {

        @Volatile
        private var INSTANCE: WeatherRoomDatabase? = null

        fun getDatabase(context: Context): WeatherRoomDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {

                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    WeatherRoomDatabase::class.java,
                    "weather_app_database")
                    .build()
                INSTANCE = instance
                return instance
            }
        }

    }


}