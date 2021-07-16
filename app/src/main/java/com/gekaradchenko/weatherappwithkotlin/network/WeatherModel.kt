package com.gekaradchenko.weatherappwithkotlin.network

data class WeatherModel(
    val current: Current,
    val hourly: List<Hourly>,
    val lat: Double,
    val lon: Double,
    val timezone: String,
    val timezone_offset: Double,
)

data class Current(
    val clouds: Double,
    val dew_point: Double,
    val dt: Double,
    val feels_like: Double,
    val humidity: Double,
    val pressure: Double,
    val sunrise: Double,
    val sunset: Double,
    val temp: Double,
    val uvi: Double,
    val visibility: Double,
    val weather: List<Weather>,
    val wind_deg: Double,
    val wind_speed: Double,
)

data class Hourly(
    val clouds: Double,
    val dew_point: Double,
    val dt: Double,
    val feels_like: Double,
    val humidity: Double,
    val pop: Double,
    val pressure: Double,
    val temp: Double,
    val uvi: Double,
    val visibility: Double,
    val weather: List<WeatherX>,
    val wind_deg: Double,
    val wind_gust: Double,
    val wind_speed: Double,
)


data class Weather(
    val description: String,
    val icon: String,
    val id: Int,
    val main: String,
)

data class WeatherX(
    val description: String,
    val icon: String,
    val id: Double,
    val main: String,
)