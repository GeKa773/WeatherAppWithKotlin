package com.gekaradchenko.weatherappwithkotlin.viewpager

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.gekaradchenko.weatherappwithkotlin.R
import com.gekaradchenko.weatherappwithkotlin.databinding.FragmentViewPagerBinding

class ViewPagerFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding: FragmentViewPagerBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_view_pager, container, false
        )

        val fragmentList = arrayListOf<Fragment>(
            Page1Fragment(),
            Page2Fragment(),
            Page3Fragment()
        )
        val adapter = ViewPagerAdapter(
            fragmentList,
            requireActivity().supportFragmentManager,
            lifecycle
        )
        binding.viewPager.adapter= adapter





        return binding.root
    }


}