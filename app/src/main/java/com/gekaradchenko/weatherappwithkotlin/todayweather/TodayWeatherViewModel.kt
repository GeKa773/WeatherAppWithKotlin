package com.gekaradchenko.weatherappwithkotlin.todayweather

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.gekaradchenko.weatherappwithkotlin.R

import com.gekaradchenko.weatherappwithkotlin.network.WeatherApi
import com.gekaradchenko.weatherappwithkotlin.unit.UnitSome
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

const val LAT = 50.4547
const val LON = 30.5238
const val EXCLUDE = "daily"
const val APPID_KEY = "92d56959b946a47f9ad21b1c5c911179"

class TodayWeatherViewModel(application: Application) : AndroidViewModel(application) {

    val app = application
    private val viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)


    private val _timeZone = MutableLiveData<String>()
    val timeZone: LiveData<String>
        get() = _timeZone

    private val _dayToday = MutableLiveData<String>()
    val dayToday: LiveData<String>
        get() = _dayToday

    private val _location = MutableLiveData<String>()
    val location: LiveData<String>
        get() = _location

    private val _temp = MutableLiveData<String>()
    val temp: LiveData<String>
        get() = _temp

    private val _icon = MutableLiveData<Int>()
    val icon: LiveData<Int>
        get() = _icon

    private val _humid = MutableLiveData<String>()
    val humid: LiveData<String>
        get() = _humid

    private val _wildSpeed = MutableLiveData<String>()
    val wildSpeed: LiveData<String>
        get() = _wildSpeed
    private val _timeNow = MutableLiveData<String>()
    val timeNow: LiveData<String>
        get() = _timeNow


    init {
        _dayToday.value = UnitSome.getDateToday(app)
        _timeNow.value = UnitSome.getTimeNow()
        getWeatherReal()
    }

    private fun getWeatherReal() {
        coroutineScope.launch {

            val getWeatherDeferred = WeatherApi.retrofitService.getWeather(
                LAT, LON,
                EXCLUDE,
                APPID_KEY
            )

            try {
                var result = getWeatherDeferred.await()
                _timeZone.value = result.timezone
                _location.value = "${result.lat}/${result.lon}"
                _temp.value = UnitSome.getTemp(result.current.temp)
                _icon.value = UnitSome.getWeatherIcon(result.current.weather[0].id,
                    result.timezone_offset)
                _humid.value = "${app.getString(R.string.humidity)} ${result.current.humidity}%"
                _wildSpeed.value =
                    "${app.getString(R.string.wind_speed)} ${result.current.wind_speed} ${
                        app.getString(R.string.m_c)
                    }"
            } catch (t: Throwable) {
                Log.d("TodayWeatherViewModel", "getWeatherReal: ${t.message}")
            }
        }
    }


//    private val _navigateToWeekWeather = MutableLiveData<Long>()
//    val navigateToWeekWeather
//        get() = _navigateToWeekWeather
//
//    fun onSleepNightClicked(id: Long) {
//        _navigateToWeekWeather.value = id
//    }
//
//    fun onSleepDataQualityNavigated() {
//        _navigateToWeekWeather.value = null
//    }








    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}