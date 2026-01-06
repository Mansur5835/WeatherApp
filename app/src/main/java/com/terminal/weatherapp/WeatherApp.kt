package com.terminal.weatherapp

import android.app.Application
import com.terminal.weatherapp.di.ApplicationComponent
import com.terminal.weatherapp.di.DaggerApplicationComponent

class WeatherApp: Application() {
    lateinit var applicationComponent: ApplicationComponent


    override fun onCreate() {
        super.onCreate()
        applicationComponent  = DaggerApplicationComponent.factory().create(this)
    }
}