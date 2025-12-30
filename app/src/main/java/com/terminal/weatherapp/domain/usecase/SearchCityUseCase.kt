package com.terminal.weatherapp.domain.usecase

import com.terminal.weatherapp.domain.repository.FavoriteRepository
import com.terminal.weatherapp.domain.repository.SearchRepository
import retrofit2.http.Query
import javax.inject.Inject

class SearchCityUseCase @Inject constructor(
    private val repository: SearchRepository
) {
    suspend operator fun invoke(query: String) = repository.search(query)
}