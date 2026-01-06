package com.terminal.weatherapp.presentation.deteils

import kotlinx.coroutines.flow.StateFlow

interface DetailsComponent {

    val model: StateFlow<DetailsStore.State >

    fun onCLickBack()

    fun onclickChangeFavoriteStatus()
}