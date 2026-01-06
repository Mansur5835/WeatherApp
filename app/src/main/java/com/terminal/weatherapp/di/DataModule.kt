package com.terminal.weatherapp.di

import android.content.Context
import com.terminal.weatherapp.data.local.db.FavoriteCitiesDao
import com.terminal.weatherapp.data.local.db.FavoriteDataBase
import com.terminal.weatherapp.data.network.api.ApiFactory
import com.terminal.weatherapp.data.network.api.ApiService
import com.terminal.weatherapp.data.repository.FavoriteRepositoryImpl
import com.terminal.weatherapp.data.repository.SearchRepositoryImpl
import com.terminal.weatherapp.data.repository.WeatherRepositoryImpl
import com.terminal.weatherapp.domain.repository.FavoriteRepository
import com.terminal.weatherapp.domain.repository.SearchRepository
import com.terminal.weatherapp.domain.repository.WeatherRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
interface DataModule {


    @[ApplicationScope Binds]
    fun bindFavoriteRepository(impl: FavoriteRepositoryImpl): FavoriteRepository

    @[ApplicationScope Binds]
    fun bindWeatherRepository(impl: WeatherRepositoryImpl): WeatherRepository

    @[ApplicationScope Binds]
    fun bindSearchRepository(impl: SearchRepositoryImpl): SearchRepository

    companion object {
        @[ApplicationScope Provides]
        fun provideApiService(): ApiService = ApiFactory.apiService

        @[ApplicationScope Provides]
        fun provideFavoriteDateBase(context: Context): FavoriteDataBase {
            return FavoriteDataBase.getInstance(context)
        }

        @[ApplicationScope Provides]
        fun provideFavoriteCitiesDao(db: FavoriteDataBase): FavoriteCitiesDao =
            db.favoriteCitiesDao()
    }


}