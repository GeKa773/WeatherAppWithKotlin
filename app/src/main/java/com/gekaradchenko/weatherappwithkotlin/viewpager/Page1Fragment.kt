package com.gekaradchenko.weatherappwithkotlin.viewpager

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.viewpager2.widget.ViewPager2
import com.gekaradchenko.weatherappwithkotlin.R

import com.gekaradchenko.weatherappwithkotlin.databinding.FragmentPage1Binding
import com.gekaradchenko.weatherappwithkotlin.unit.UnitSome

class Page1Fragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding: FragmentPage1Binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_page1, container, false
        )

        val viewPager = activity?.findViewById<ViewPager2>(R.id.viewPager)


        binding.firstNextButton.setOnClickListener {

            viewPager?.isUserInputEnabled = false
            viewPager?.currentItem = 1
        }
        binding.secondAndroidTextView.setOnClickListener{
           UnitSome.showDialog(requireContext())
        }



        return binding.root
    }


}