package com.example.liquidweather3.data.remote

import retrofit2.http.GET
import retrofit2.http.Query

interface OpenMeteoApi {
    @GET("v1/forecast")
    suspend fun getWeatherData(
        @Query("latitude") lat: Double = 55.75,
        @Query("longitude") lon: Double = 37.61,
        @Query("current") current: String = "temperature_2m,relative_humidity_2m,apparent_temperature,weather_code,wind_speed_10m",
        @Query("daily") daily: String = "temperature_2m_max,temperature_2m_min,uv_index_max,weather_code"
    ): WeatherResponse
}

data class WeatherResponse(
    val current: CurrentWeather,
    val daily: DailyWeather
)

data class CurrentWeather(
    val temperature_2m: Double,
    val relative_humidity_2m: Int,
    val apparent_temperature: Double,
    val weather_code: Int,
    val wind_speed_10m: Double
)

data class DailyWeather(
    val time: List<String>,
    val temperature_2m_max: List<Double>,
    val temperature_2m_min: List<Double>,
    val uv_index_max: List<Double>,
    val weather_code: List<Int>
)
