package com.terminal.weatherapp.data.mapper

import com.terminal.weatherapp.data.local.model.CityDbModel
import com.terminal.weatherapp.domain.entity.City

fun City.toDbModel(): CityDbModel = CityDbModel(id, name, country)


fun CityDbModel.toEntity(): City = City(id, name, country)

fun List<CityDbModel>.toEntities(): List<City> = map { it.toEntity() }