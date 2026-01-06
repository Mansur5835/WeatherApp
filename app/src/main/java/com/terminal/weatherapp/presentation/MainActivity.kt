package com.terminal.weatherapp.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.arkivanov.decompose.defaultComponentContext
import com.terminal.weatherapp.WeatherApp
import com.terminal.weatherapp.data.network.api.ApiFactory
import com.terminal.weatherapp.domain.usecase.ChangeFavoriteStateUseCase
import com.terminal.weatherapp.domain.usecase.SearchCityUseCase
import com.terminal.weatherapp.presentation.root.DefaultRootComponent
import com.terminal.weatherapp.presentation.root.RootComponent
import com.terminal.weatherapp.presentation.root.RootContent
import com.terminal.weatherapp.presentation.ui.theme.WeatherAppTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.Dispatcher
import javax.inject.Inject

class MainActivity : ComponentActivity() {


    @Inject
    lateinit var rootComponentFactory: DefaultRootComponent.Factory

//    @Inject
//    lateinit var useCase: SearchCityUseCase
//    @Inject
//    lateinit var change: ChangeFavoriteStateUseCase
//
//


    override fun onCreate(savedInstanceState: Bundle?) {
        (applicationContext as WeatherApp).applicationComponent.inject(this)
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

//        CoroutineScope(Dispatchers.Main).launch {
//           val asdf= useCase.invoke("под")
//
//            asdf.forEach {
//                change.addToFavorite(it)
//            }
//        }


        setContent {
            WeatherAppTheme {
                RootContent(
                    component = rootComponentFactory.create(defaultComponentContext())
                )
            }
        }
    }
}