package com.terminal.weatherapp.domain.usecase

import com.terminal.weatherapp.domain.repository.FavoriteRepository
import javax.inject.Inject

class ObserveFavoriteStateUseCase @Inject constructor(
    private val repository: FavoriteRepository
) {
    operator fun invoke(cityId: Int) = repository.observeIsFavorite(cityId)
}