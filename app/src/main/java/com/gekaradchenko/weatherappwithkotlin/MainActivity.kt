package com.gekaradchenko.weatherappwithkotlin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.gekaradchenko.weatherappwithkotlin.viewpager.PageActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    }


    fun goNext(view: View) {
        startActivity(Intent(this, PageActivity::class.java))
    }
}