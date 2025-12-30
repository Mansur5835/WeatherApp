package com.terminal.weatherapp.presentation.favorite

import com.arkivanov.decompose.ComponentContext

class DefaultFavoriteComponent(
    componentContext: ComponentContext
) : FavoriteComponent, ComponentContext by componentContext {

}