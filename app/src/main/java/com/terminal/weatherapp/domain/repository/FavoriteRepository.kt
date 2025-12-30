package com.terminal.weatherapp.domain.repository

import com.terminal.weatherapp.domain.entity.City
import kotlinx.coroutines.flow.Flow

interface FavoriteRepository {

    val favoritesCities: Flow<City>

    fun observeIsFavorite(cityId: Int): Flow<Boolean>

    suspend fun addToFavorite(city: City)


    suspend fun removeFromFavorite(cityId: Int)

}