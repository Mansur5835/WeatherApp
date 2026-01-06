package com.terminal.weatherapp.presentation.search

import com.terminal.weatherapp.domain.entity.City
import kotlinx.coroutines.flow.StateFlow

interface SearchComponent {

    val model : StateFlow<SearchStore.State>

    fun changeSearchQuery(query:String)

    fun onClickBack()

    fun onClickCity(city: City)

    fun onClickSearch()


}