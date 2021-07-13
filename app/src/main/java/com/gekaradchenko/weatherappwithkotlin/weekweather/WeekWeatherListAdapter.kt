package com.gekaradchenko.weatherappwithkotlin.weekweather

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

import com.gekaradchenko.weatherappwithkotlin.databinding.WeekWeatherRecyclerViewItemBinding

class WeekWeatherListAdapter(val clickListener: WeekWeatherListener) :
    ListAdapter<WeekWeather, WeekWeatherListAdapter.WeekWeatherViewHolder>(DiffCallback) {

    class WeekWeatherViewHolder(private var binding: WeekWeatherRecyclerViewItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(weekWeather: WeekWeather, clickListener: WeekWeatherListener) {
            binding.weekWeather = weekWeather
            binding.clickListener = clickListener
            binding.executePendingBindings()
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeekWeatherViewHolder {
        return WeekWeatherViewHolder(WeekWeatherRecyclerViewItemBinding.inflate(LayoutInflater.from(
            parent.context)))
    }

    override fun onBindViewHolder(holder: WeekWeatherViewHolder, position: Int) {
        holder.bind(getItem(position)!!, clickListener)


    }


    companion object DiffCallback : DiffUtil.ItemCallback<WeekWeather>() {
        override fun areItemsTheSame(oldItem: WeekWeather, newItem: WeekWeather): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: WeekWeather, newItem: WeekWeather): Boolean {
            return oldItem.count == newItem.count
        }

    }


}

class WeekWeatherListener(val clickListener: (weatherId: Int) -> Unit) {
    fun onClick(weekWeather: WeekWeather) = clickListener(weekWeather.count)

}
