package com.gekaradchenko.weatherappwithkotlin.weekweather

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

import com.gekaradchenko.weatherappwithkotlin.network.WeatherApi
import com.gekaradchenko.weatherappwithkotlin.todayweather.APPID_KEY
import com.gekaradchenko.weatherappwithkotlin.todayweather.EXCLUDE
import com.gekaradchenko.weatherappwithkotlin.todayweather.LAT
import com.gekaradchenko.weatherappwithkotlin.todayweather.LON
import com.gekaradchenko.weatherappwithkotlin.unit.UnitSome
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class WeekWeatherViewModel(application: Application) : AndroidViewModel(application) {
    val app = application
    private val viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)


    private val _list = MutableLiveData<List<WeekWeather>>()
    val list: LiveData<List<WeekWeather>>
        get() = _list

    private val _timeZone = MutableLiveData<String>()
    val timeZone: LiveData<String>
        get() = _timeZone

    private val _location = MutableLiveData<String>()
    val location: LiveData<String>
        get() = _location

    init {

        getWeekWeatherReal()
    }


    private fun getWeekWeatherReal() {
        coroutineScope.launch {

            val getWeatherDeferred = WeatherApi.retrofitService.getWeather(
                LAT, LON,
                EXCLUDE,
                APPID_KEY
            )

            try {
                var result = getWeatherDeferred.await()
                val listWeather = ArrayList<WeekWeather>()

                _timeZone.value = result.timezone
                _location.value = "${result.lat}/${result.lon}"
                for (i in result.hourly.indices) {
                    listWeather.add(WeekWeather(
                        UnitSome.getWeekWeatherIcon(result.current.weather.get(0).id,
                            result.timezone_offset,
                            i),
                        UnitSome.getHoursString(app, i),
                        UnitSome.getForecastString(app,
                            result.current.temp,
                            result.current.humidity,
                            result.current.wind_speed),
                        i
                    ))
                }
                _list.value = listWeather

                Log.d("GetWeekWeatherReal", "some: ${listWeather?.size}")


            } catch (t: Throwable) {
                Log.d("TodayWeatherViewModel", "getWeatherReal: ${t.message}")
            }
        }
    }

    private val _navigateToTodayWeather = MutableLiveData<Int>()
    val navigateToTodayWeather
        get() = _navigateToTodayWeather

    fun onWeekWeatherClicked(b: Int) {
        _navigateToTodayWeather.value = b
    }

    fun onWeekWeatherNavigated() {
        _navigateToTodayWeather.value = null
    }


    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}
