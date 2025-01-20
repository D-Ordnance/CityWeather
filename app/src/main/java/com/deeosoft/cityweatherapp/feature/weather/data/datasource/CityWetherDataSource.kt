package com.deeosoft.cityweatherapp.feature.weather.data.datasource

import com.deeosoft.cityweatherapp.feature.weather.domain.entity.Weather
import com.deeosoft.cityweatherapp.feature.weather.domain.entity.WeatherForecast

interface CityWeatherDataSource {
    suspend fun remoteSource(city: String): List<Weather?>
    suspend fun localSource(city: String): List<Weather?>
    suspend fun getAllSavedCityWeather(): List<Weather?>
    suspend fun getAllFavoriteWeather(): List<Weather?>
    suspend fun saveFavoriteState(id: Double, state: Boolean)
    suspend fun getWeatherForecast(city: String, cityId: Double): List<WeatherForecast?>
}