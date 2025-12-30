package com.terminal.weatherapp.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.terminal.weatherapp.data.network.api.ApiFactory
import com.terminal.weatherapp.presentation.ui.theme.WeatherAppTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.Dispatcher

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val api = ApiFactory.apiService

        CoroutineScope(Dispatchers.Main).launch {
            val city = api.searchCity("Toshkent")
            println("city---> $city")
        }

        setContent {
            WeatherAppTheme {

            }
        }
    }
}