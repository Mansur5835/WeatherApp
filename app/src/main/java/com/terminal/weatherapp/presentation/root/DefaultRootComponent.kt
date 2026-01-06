package com.terminal.weatherapp.presentation.root

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.DelicateDecomposeApi
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.router.stack.push
import com.arkivanov.decompose.value.Value
import com.terminal.weatherapp.domain.entity.City
import com.terminal.weatherapp.presentation.deteils.DefaultDetailsComponent
import com.terminal.weatherapp.presentation.favorite.DefaultFavoriteComponent
import com.terminal.weatherapp.presentation.search.DefaultSearchComponent
import com.terminal.weatherapp.presentation.search.OpenReason
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.serialization.Serializable

class DefaultRootComponent @AssistedInject constructor(
    private val detailsComponentFactory: DefaultDetailsComponent.Factory,
    private val favoriteComponentFactory: DefaultFavoriteComponent.Factory,
    private val searchComponentFactory: DefaultSearchComponent.Factory,
    @Assisted("componentContext") componentContext: ComponentContext,
) : RootComponent, ComponentContext by componentContext {

    private val navigation = StackNavigation<Config>()

    override val stack: Value<ChildStack<*, RootComponent.Child>> = childStack(
        source = navigation,
        initialConfiguration = Config.Favorite,
        handleBackButton = true,
        childFactory = ::child,
        serializer = Config.serializer()

    )

    @OptIn(DelicateDecomposeApi::class)
    private fun child(
        config: Config,
        componentContext: ComponentContext,
    ): RootComponent.Child {
        return when (config) {
            is Config.Details -> {
                val component = detailsComponentFactory.create(
                    city = config.city,
                    onClickBack = {
                        navigation.pop()
                    },
                    componentContext = componentContext
                )

                RootComponent.Child.Details(component)

            }

            Config.Favorite -> {
                val component = favoriteComponentFactory.create(
                    onSearchClick = {
                        navigation.push(Config.Search(OpenReason.RegularSearch))
                    },
                    onCityItemClicked = {
                        navigation.push(Config.Details(it))
                    },
                    onAddToFavoriteClick = {
                        navigation.push(Config.Search(OpenReason.AddToFavorite))
                    },
                    componentContext = componentContext
                )

                RootComponent.Child.Favorite(component)
            }

            is Config.Search -> {
                val component = searchComponentFactory.create(
                    onBackClicked = {
                        navigation.pop()
                    },
                    onCitySavedToFavorite = {
                        navigation.pop()
                    },
                    onForecastForCityRequested = {
                        navigation.push(Config.Details(it))
                    },
                    openReason = config.openReason,
                    componentContext = componentContext
                )
                RootComponent.Child.Search(component)
            }
        }
    }


    @Serializable
    sealed interface Config {

        @Serializable
        data object Favorite : Config

        @Serializable
        data class Search(val openReason: OpenReason) : Config

        @Serializable
        data class Details(val city: City) : Config
    }


    @AssistedFactory
    interface Factory {
        fun create(
            @Assisted("componentContext") componentContext: ComponentContext,
        ): DefaultRootComponent
    }


}