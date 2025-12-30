package com.terminal.weatherapp.presentation.deteils

import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineBootstrapper
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import com.terminal.weatherapp.presentation.deteils.DetailsStore.Intent
import com.terminal.weatherapp.presentation.deteils.DetailsStore.State
import com.terminal.weatherapp.presentation.deteils.DetailsStore.Label


internal interface DetailsStore : Store<Intent, State, Label> {


    sealed interface Intent {

    }

    data class State(val todo: Unit)

    sealed interface Label {

    }

}


internal class DetailsStoreFactory(
    private val storeFactory: StoreFactory
) {


    fun create(): DetailsStore =
        object : DetailsStore,
            Store<DetailsStore.Intent, DetailsStore.State, DetailsStore.Label> by storeFactory.create(
                name = "",
                initialState = State(Unit),
                bootstrapper = BootstrapperImpl(),
                executorFactory = ::ExecutorImpl,
                reducer = ReducerImpl

            ) {

        }


    private sealed class Action {

    }

    private sealed class Msg {

    }


    private class BootstrapperImpl : CoroutineBootstrapper<Action>() {
        override fun invoke() {
            TODO("Not yet implemented")
        }

    }


    private class ExecutorImpl : CoroutineExecutor<Intent, Action, State, Msg, Label>() {


        override fun executeIntent(intent: Intent) {
            super.executeIntent(intent)
        }

    }


    private object ReducerImpl : Reducer<State, Msg> {
        override fun State.reduce(msg: Msg): State = State(Unit)

    }


}