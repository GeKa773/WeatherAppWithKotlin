package com.gekaradchenko.weatherappwithkotlin.todayweather

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.gekaradchenko.weatherappwithkotlin.R
import com.gekaradchenko.weatherappwithkotlin.databinding.FragmentTodayWeatherBinding


class TodayWeatherFragment : Fragment() {
    private val viewModel: TodayWeatherViewModel by lazy {
        ViewModelProvider(this).get(TodayWeatherViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        val binding: FragmentTodayWeatherBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_today_weather, container, false)

        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        binding.todayWeatherFragment.setOnClickListener {
//            this.findNavController()
//                .navigate(TodayWeatherFragmentDirections.actionTodayWeatherFragmentToWeekWeatherFragment())

            findNavController().navigate(R.id.action_todayWeatherFragment_to_weekWeatherFragment)
        }


        return binding.root
    }


}