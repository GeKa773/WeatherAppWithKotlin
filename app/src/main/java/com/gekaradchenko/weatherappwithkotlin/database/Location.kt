package com.gekaradchenko.weatherappwithkotlin.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "location_table")
data class Location(
    @PrimaryKey(autoGenerate = true)
    var locationId: Long = 0L,
    var lat: Double,
    var lon: Double,
)
