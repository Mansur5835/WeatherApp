package com.terminal.weatherapp.domain.repository

import androidx.room.Query
import com.terminal.weatherapp.domain.entity.City
import com.terminal.weatherapp.domain.entity.Forecast
import com.terminal.weatherapp.domain.entity.Weather
import kotlinx.coroutines.flow.Flow

interface WeatherRepository {


    suspend fun getWeather(cityId: Int): Weather

    suspend fun getForecast(cityId: Int): Forecast

}