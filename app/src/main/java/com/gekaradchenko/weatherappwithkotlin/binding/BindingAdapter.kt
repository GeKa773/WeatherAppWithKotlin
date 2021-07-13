package com.gekaradchenko.weatherappwithkotlin

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

@BindingAdapter("imageSet")
fun setImageView(imageView: ImageView, imageCrs: Int) {
    imageCrs?.let {
        Glide.with(imageView.context)
            .load(it)
            .into(imageView)
    }
}

@BindingAdapter("setString")
fun TextView.setString(string:String?){
    string?.let {
        text= "$string"
    }

}