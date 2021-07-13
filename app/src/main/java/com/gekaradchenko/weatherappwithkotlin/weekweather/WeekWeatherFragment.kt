package com.gekaradchenko.weatherappwithkotlin.weekweather

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.gekaradchenko.weatherappwithkotlin.R
import com.gekaradchenko.weatherappwithkotlin.databinding.FragmentWeekWeatherBinding


class WeekWeatherFragment : Fragment() {
    private val viewModel: WeekWeatherViewModel by lazy {
        ViewModelProvider(this).get(WeekWeatherViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        val binding: FragmentWeekWeatherBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_week_weather, container, false)


        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        val adapter = WeekWeatherListAdapter(WeekWeatherListener {
            viewModel.onWeekWeatherClicked(it)
        })
        binding.weekRecyclerView.adapter = adapter
        viewModel.list.observe(viewLifecycleOwner, Observer {
            adapter.submitList(it)
        })

        viewModel.navigateToTodayWeather.observe(viewLifecycleOwner, Observer {

            it?.let {
//                this.findNavController().navigate(
//                    WeekWeatherFragmentDirections.actionWeekWeatherFragmentToTodayWeatherFragment()
//                )

                findNavController().navigate(R.id.action_weekWeatherFragment_to_todayWeatherFragment)

                viewModel.onWeekWeatherNavigated()
            }
        })




        return binding.root
    }


}