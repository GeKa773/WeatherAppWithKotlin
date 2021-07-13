package com.gekaradchenko.weatherappwithkotlin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.gekaradchenko.weatherappwithkotlin.databinding.ActivityWeatherBinding


class WeatherActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityWeatherBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_weather)

        val navController = findNavController(R.id.fragmentContainerView2)

        binding.bottomNavigationView.setupWithNavController(navController)
    }
}