package com.example.liquidweather3.worker

import android.content.Context
import androidx.room.Room
import androidx.work.*
import com.example.liquidweather3.data.local.WeatherDatabase
import com.example.liquidweather3.data.local.WeatherEntity
import com.example.liquidweather3.data.remote.OpenMeteoApi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class WeatherSyncWorker(
    appContext: Context,
    params: WorkerParameters
) : CoroutineWorker(appContext, params) {

    override suspend fun doWork(): Result {
        return try {
            val api = Retrofit.Builder()
                .baseUrl("https://api.open-meteo.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(OpenMeteoApi::class.java)

            val response = api.getWeatherData()
            
            val database = Room.databaseBuilder(
                applicationContext,
                WeatherDatabase::class.java,
                "weather_db"
            ).build()
            
            val dao = database.weatherDao()

            dao.saveWeather(
                WeatherEntity(
                    id = 1,
                    temperature = response.current.temperature_2m,
                    humidity = response.current.relative_humidity_2m,
                    apparentTemp = response.current.apparent_temperature,
                    weatherCode = response.current.weather_code,
                    windSpeed = response.current.wind_speed_10m,
                    updatedAt = System.currentTimeMillis()
                )
            )
            Result.success()
        } catch (e: Exception) {
            Result.retry()
        }
    }

    companion object {
        fun enqueuePeriodicWork(context: Context) {
            val constraints = Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build()

            val request = PeriodicWorkRequestBuilder<WeatherSyncWorker>(2, TimeUnit.HOURS)
                .setConstraints(constraints)
                .setBackoffCriteria(BackoffPolicy.LINEAR, 30, TimeUnit.MINUTES)
                .build()

            WorkManager.getInstance(context).enqueueUniquePeriodicWork(
                "WeatherSync",
                ExistingPeriodicWorkPolicy.KEEP,
                request
            )
        }
    }
}
