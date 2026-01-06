package com.terminal.weatherapp.data.repository

import com.terminal.weatherapp.data.mapper.toEntities
import com.terminal.weatherapp.data.mapper.toEntity
import com.terminal.weatherapp.data.network.api.ApiService
import com.terminal.weatherapp.domain.entity.City
import com.terminal.weatherapp.domain.repository.SearchRepository
import javax.inject.Inject

class SearchRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
) : SearchRepository {
    override suspend fun search(query: String): List<City> {
     return   apiService.searchCity(query).toEntities()
    }
}