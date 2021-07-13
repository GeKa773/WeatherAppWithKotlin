package com.gekaradchenko.weatherappwithkotlin.viewpager

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.gekaradchenko.weatherappwithkotlin.R

import com.gekaradchenko.weatherappwithkotlin.WeatherActivity
import com.gekaradchenko.weatherappwithkotlin.databinding.FragmentPage3Binding
import com.gekaradchenko.weatherappwithkotlin.unit.UnitSome


class Page3Fragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding: FragmentPage3Binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_page3, container, false
        )



        binding.thirdNextButton.setOnClickListener {
            startActivity(Intent(requireContext(),WeatherActivity::class.java))
        }
        binding.secondAndroidTextView.setOnClickListener{
            UnitSome.showDialog(requireContext())
        }


        return binding.root
    }


}