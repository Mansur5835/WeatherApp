package com.terminal.weatherapp.domain.usecase

import com.terminal.weatherapp.domain.entity.City
import com.terminal.weatherapp.domain.repository.FavoriteRepository
import com.terminal.weatherapp.domain.repository.WeatherRepository
import javax.inject.Inject

class GetForecastUseCase @Inject constructor(
    private val repository: WeatherRepository
) {
    suspend operator fun invoke(cityId: Int) = repository.getForecast(cityId)
}