package com.example.dvt.helper_class

data class WeatherForecast(
    val cod: Int,
    val cnt:Int,
    val list:List<Forecast>
)

data class Forecast(
    val dt: Int,
    val main: Main,
    val weather: List<Weather>,
    val clouds: Clouds,
    val wind: Wind,
    val visibility: Double,
    val dt_txt: String,

    )



data class TodayWeatherData(

    val coord: CoordDetails,
    val weather: List<Weather>,
    val base: String,
    val main: Main,
    val visibility: Double,
    val wind: Wind,
    val clouds: Clouds,
    val dt: Int,
    val sys: Sys,
    val timezone:Int,
    val id:Int,
    val name: String,
    val cod: Int

)

data class Sys(val type:Int, val id:Int, val country: String, val sunrise:Int, val sunset:Int)
data class Clouds(val all:Int)
data class Wind(val speed: Double, val deg: Double)
data class Main(val temp: Double, val feels_like: Double, val temp_min: Double, val temp_max: Double, val pressure: Double, val humidity: Double)
data class CoordDetails(val lon: Double, val  lat: Double)
data class Weather(val id: Int, val main: String, val description: String, val icon:String)