package com.terminal.weatherapp.presentation.favorite

import com.terminal.weatherapp.domain.entity.City
import kotlinx.coroutines.flow.StateFlow

interface FavoriteComponent {

    val model: StateFlow<FavoriteStore.State>

    fun onClickSearch()

    fun onClickAddFavorite()

    fun onCityItemClick(city: City)

}