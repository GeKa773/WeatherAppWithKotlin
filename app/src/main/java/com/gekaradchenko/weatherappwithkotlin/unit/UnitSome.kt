package com.gekaradchenko.weatherappwithkotlin.unit

import android.Manifest
import android.app.Application
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import com.gekaradchenko.weatherappwithkotlin.R
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener
import java.text.SimpleDateFormat
import java.util.*


const val TEMP = 273.15

object UnitSome {
    fun showDialog(context: Context) {
        val dialog = Dialog(context)
        dialog.setContentView(R.layout.custom_dialog)
        dialog.window!!.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        dialog.setCancelable(true)
        val dialogPrivateButton = dialog.findViewById<Button>(R.id.dialogPrivateButton)
        val dialogTermButton = dialog.findViewById<Button>(R.id.dialogTermButton)
        dialogPrivateButton.setOnClickListener {
            Toast.makeText(context, "Private policy", Toast.LENGTH_SHORT).show()
            dialog.cancel()
        }

        dialogTermButton.setOnClickListener {
            Toast.makeText(context, "Term of using", Toast.LENGTH_SHORT).show()
            dialog.cancel()
        }
        dialog.show()
    }


    fun getWeatherIcon(id: Int, timeZone: Double): Int {
        val date = Date()
        val localTime = date.time
        val localOffset = (date.timezoneOffset * 60000).toLong()
        val utc = localTime + localOffset
        val x = (utc + 1000 * timeZone).toLong()
        val timeZoneDate = Date(x)

        val c = Calendar.getInstance()
        c.time = timeZoneDate
        var timeOfDay = c[Calendar.HOUR_OF_DAY]

        return when (timeOfDay) {

            in 6..20 ->
                when (id) {
                    in 0..299 -> R.drawable.rain_with_thunderstorm
                    in 300..503 -> R.drawable.rain
                    in 504..599 -> R.drawable.rain_with_snow
                    in 690..700 -> R.drawable.snow
                    in 701..799 -> R.drawable.sun_and_clouds
                    800 -> R.drawable.sun
                    else -> R.drawable.clounds
                }

            else -> when (id) {
                in 0..299 -> R.drawable.night_rain
                in 300..503 -> R.drawable.night_rain
                in 504..599 -> R.drawable.night_snow
                in 690..700 -> R.drawable.night_snow
                in 701..799 -> R.drawable.night_clouds
                800 -> R.drawable.moon
                else -> R.drawable.night_clouds

            }
        }

    }


    fun getTemp(temp: Double): String {
        val t = temp - TEMP
        val stringTemp = String.format("%.1f", t)
        return if (t > 0) {
            "+$stringTemp"
        } else {
            "-$stringTemp"
        }
    }

    fun getDateToday(app: Application): String {
        val date = Date()
        val format = SimpleDateFormat("dd.MM.yyyy")
        val dateString = format.format(date)
        val calendar = Calendar.getInstance()
        return when (calendar.get(Calendar.DAY_OF_WEEK)) {
            Calendar.MONDAY -> "${app.getString(R.string.monday)},${dateString}"
            Calendar.TUESDAY -> "${app.getString(R.string.tuesday)},${dateString}"
            Calendar.WEDNESDAY -> "${app.getString(R.string.wednesday)},${dateString}"
            Calendar.THURSDAY -> "${app.getString(R.string.thursday)},${dateString}"
            Calendar.FRIDAY -> "${app.getString(R.string.friday)},${dateString}"
            Calendar.SATURDAY -> "${app.getString(R.string.saturday)},${dateString}"
            else -> "${app.getString(R.string.sunday)},${dateString}"

        }

    }

    fun getTimeNow() = SimpleDateFormat("HH:mm").format(Date())


    fun getWeekWeatherIcon(id: Int, timeZone: Double, position: Int): Int {
        val date = Date()
        val localTime = date.time
        val localOffset = (date.timezoneOffset * 60000).toLong()
        val utc = localTime + localOffset
        val x = (utc + 1000 * timeZone).toLong()
        val timeZoneDate = Date(x)

        val c = Calendar.getInstance()
        c.time = timeZoneDate
        var timeOfDay = c[Calendar.HOUR_OF_DAY]
        timeOfDay += position
        while (timeOfDay > 24) {
            timeOfDay -= 24
        }
        return when (timeOfDay) {

            in 6..20 ->
                when (id) {
                    in 0..299 -> R.drawable.rain_with_thunderstorm
                    in 300..503 -> R.drawable.rain
                    in 504..599 -> R.drawable.rain_with_snow
                    in 690..700 -> R.drawable.snow
                    in 701..799 -> R.drawable.sun_and_clouds
                    800 -> R.drawable.sun
                    else -> R.drawable.clounds
                }

            else -> when (id) {
                in 0..299 -> R.drawable.night_rain
                in 300..503 -> R.drawable.night_rain
                in 504..599 -> R.drawable.night_snow
                in 690..700 -> R.drawable.night_snow
                in 701..799 -> R.drawable.night_clouds
                800 -> R.drawable.moon
                else -> R.drawable.night_clouds

            }
        }

    }

    fun getHoursString(app: Application, position: Int): String {
        return when (position) {
            0 -> app.getString(R.string.now)
            1 -> "+1 ${app.getString(R.string.hour)}"
            else -> "+$position ${app.getString(R.string.hours)}"

        }

    }

    fun getForecastString(
        app: Application,
        temp: Double,
        humid: Double,
        windSpeed: Double,
    ): String = "${getTemp(temp)}/${humid}%/${windSpeed}${app.getString(R.string.m_c)}"





}
