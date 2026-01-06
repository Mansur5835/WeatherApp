package com.terminal.weatherapp.data.mapper

import com.terminal.weatherapp.data.network.dto.WeatherCurrentDto
import com.terminal.weatherapp.data.network.dto.WeatherDto
import com.terminal.weatherapp.data.network.dto.WeatherForecastDto
import com.terminal.weatherapp.domain.entity.Forecast
import com.terminal.weatherapp.domain.entity.Weather
import java.util.Calendar
import java.util.Date

fun WeatherCurrentDto.toEntity() = current.toEntity()

fun WeatherDto.toEntity() = Weather(
    tempC = tempC,
    conditionText = condition.text,
    conditionUrl = condition.iconUrl.correctImageUrl(),
    date = date.toCalendar()
)

fun WeatherForecastDto.toEntity() = Forecast(
    currentWeather = current.toEntity(),
    upcoming = forecast.forecastDay.drop(1).map { dayDto ->
        val dayWeatherDto = dayDto.day
        Weather(
            tempC = dayWeatherDto.tempC,
            conditionText = dayWeatherDto.condition.text,
            conditionUrl = dayWeatherDto.condition.iconUrl.correctImageUrl(),
            date = dayDto.date.toCalendar()

        )
    }
)


private fun Long.toCalendar() = Calendar.getInstance().apply {
    time = Date(this@toCalendar * 1000)
}

private fun String.correctImageUrl() =
    "https:$this".replace(oldValue = "64x64", newValue = "128x128")