package com.terminal.weatherapp.presentation.root

import androidx.compose.runtime.Composable
import com.arkivanov.decompose.extensions.compose.stack.Children
import com.terminal.weatherapp.presentation.deteils.DetailsContent
import com.terminal.weatherapp.presentation.favorite.FavoriteContent
import com.terminal.weatherapp.presentation.search.SearchContent


@Composable
fun RootContent(component: RootComponent) {

    Children(stack = component.stack) {
        when (val instance = it.instance) {
            is RootComponent.Child.Details -> {
                DetailsContent(component = instance.component)
            }

            is RootComponent.Child.Favorite -> {
                FavoriteContent(component = instance.component)
            }

            is RootComponent.Child.Search -> {
                SearchContent(component = instance.component)
            }
        }
    }
}