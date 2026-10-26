package com.example.liquidweather3.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.example.liquidweather3.ui.screens.WeatherScreen
import com.example.liquidweather3.ui.theme.LiquidWeatherTheme
import com.example.liquidweather3.worker.WeatherSyncWorker

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Запуск фонового периодического обновления данных о погоде
        WeatherSyncWorker.enqueuePeriodicWork(applicationContext)

        setContent {
            LiquidWeatherTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    WeatherScreen()
                }
            }
        }
    }
}
