package com.terminal.weatherapp.presentation.deteils

import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineBootstrapper
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import com.terminal.weatherapp.domain.entity.City
import com.terminal.weatherapp.domain.entity.Forecast
import com.terminal.weatherapp.domain.usecase.ChangeFavoriteStateUseCase
import com.terminal.weatherapp.domain.usecase.GetForecastUseCase
import com.terminal.weatherapp.domain.usecase.ObserveFavoriteStateUseCase
import com.terminal.weatherapp.presentation.deteils.DetailsStore.Intent
import com.terminal.weatherapp.presentation.deteils.DetailsStore.State
import com.terminal.weatherapp.presentation.deteils.DetailsStore.Label
import kotlinx.coroutines.launch
import javax.inject.Inject


 interface DetailsStore : Store<Intent, State, Label> {


    sealed interface Intent {
        data object ClickBack : Intent
        data object ClickChangeFavoriteStatus : Intent
    }

    data class State(
        val city: City,
        val isFavorite: Boolean,
        val forecastState: ForecastState,
    ) {
        sealed interface ForecastState {
            data object Initial : ForecastState
            data object Loading : ForecastState
            data object Error : ForecastState
            data class Loaded(val forecast: Forecast) : ForecastState
        }
    }

    sealed interface Label {
        data object ClickBack : Label
    }

}


 class DetailsStoreFactory @Inject constructor(
    private val storeFactory: StoreFactory,
    private val getForecastUseCase: GetForecastUseCase,
    private val changeFavoriteStateUseCase: ChangeFavoriteStateUseCase,
    private val observeFavoriteStateUseCase: ObserveFavoriteStateUseCase
) {


    fun create(city: City): DetailsStore =
        object : DetailsStore,
            Store<Intent, State, Label> by storeFactory.create(
                name = "",
                initialState = State(
                    city = city,
                    isFavorite = false,
                    forecastState = State.ForecastState.Initial

                ),
                bootstrapper = BootstrapperImpl(city),
                executorFactory = ::ExecutorImpl,
                reducer = ReducerImpl
            ) {

        }


    private sealed interface Action {
        data class FavoriteStatusChanged(val isFavorite: Boolean) : Action

        data class ForecastLoaded(val forecast: Forecast) : Action

        data object ForecastLoading : Action
        data object ForecastLoadingError : Action

    }

    private sealed interface Msg {
        data class FavoriteStatusChanged(val isFavorite: Boolean) : Msg

        data class ForecastLoaded(val forecast: Forecast) : Msg

        data object ForecastLoading : Msg
        data object ForecastLoadingError : Msg

    }


    private inner class BootstrapperImpl(val city: City) : CoroutineBootstrapper<Action>() {
        override fun invoke() {
            scope.launch {
                observeFavoriteStateUseCase(city.id).collect {
                    dispatch(Action.FavoriteStatusChanged(it))
                }
            }
            scope.launch {
                dispatch(Action.ForecastLoading)
                try {
                    val forecast = getForecastUseCase(city.id)
                    dispatch(Action.ForecastLoaded(forecast))
                } catch (e: Exception) {
                    dispatch(Action.ForecastLoadingError)
                }

            }


        }

    }


    private inner class ExecutorImpl : CoroutineExecutor<Intent, Action, State, Msg, Label>() {


        override fun executeIntent(intent: Intent) {
            super.executeIntent(intent)
            when (intent) {
                Intent.ClickBack -> {
                    publish(Label.ClickBack)
                }

                Intent.ClickChangeFavoriteStatus -> {
                    val isFavorite = state().isFavorite
                    scope.launch {
                        if (isFavorite) {
                            changeFavoriteStateUseCase.removeFromFavorite(state().city.id)
                        } else {
                            changeFavoriteStateUseCase.addToFavorite(state().city)
                        }
                    }


                }
            }

        }


        override fun executeAction(action: Action) {
            super.executeAction(action)

            when (action) {
                is Action.FavoriteStatusChanged -> {


                    dispatch(Msg.FavoriteStatusChanged(action.isFavorite))
                }

                is Action.ForecastLoaded -> {

                    dispatch(Msg.ForecastLoaded(action.forecast))
                }

                Action.ForecastLoading -> {
                    dispatch(Msg.ForecastLoading)
                }

                Action.ForecastLoadingError -> {
                    dispatch(Msg.ForecastLoadingError)
                }
            }
        }

    }


    private object ReducerImpl : Reducer<State, Msg> {
        override fun State.reduce(msg: Msg): State = when (msg) {
            is Msg.FavoriteStatusChanged -> {
                copy(
                    isFavorite = msg.isFavorite
                )
            }

            is Msg.ForecastLoaded -> {
                copy(
                    forecastState = State.ForecastState.Loaded(msg.forecast)
                )
            }

            Msg.ForecastLoading -> {
                copy(
                    forecastState = State.ForecastState.Loading
                )
            }

            Msg.ForecastLoadingError -> {
                copy(
                    forecastState = State.ForecastState.Error
                )
            }
        }

    }


}