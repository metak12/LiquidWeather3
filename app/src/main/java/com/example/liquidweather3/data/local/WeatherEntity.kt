package com.example.liquidweather3.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "weather_cache")
data class WeatherEntity(
    @PrimaryKey val id: Int = 1,
    val temperature: Double,
    val humidity: Int,
    val apparentTemp: Double,
    val weatherCode: Int,
    val windSpeed: Double,
    val updatedAt: Long
)
