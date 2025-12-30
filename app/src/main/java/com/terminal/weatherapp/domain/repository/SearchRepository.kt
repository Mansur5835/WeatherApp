package com.terminal.weatherapp.domain.repository

import com.terminal.weatherapp.domain.entity.City

interface SearchRepository {
    suspend fun search(query: String): List<City>
}