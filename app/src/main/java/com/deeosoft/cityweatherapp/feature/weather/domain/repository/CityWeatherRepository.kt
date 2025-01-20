package com.deeosoft.cityweatherapp.feature.weather.domain.repository

import com.deeosoft.cityweatherapp.feature.weather.domain.entity.Weather
import com.deeosoft.cityweatherapp.feature.weather.domain.entity.WeatherForecast
import kotlinx.coroutines.flow.Flow

interface CityWeatherRepository {
    suspend fun searchByCity(city: String, forceServer: Boolean): Flow<List<Weather?>>
    suspend fun getSavedCity(): Flow<List<Weather?>>
    suspend fun getFavorite(): Flow<List<Weather?>>
    suspend fun saveFavoriteState(id: Double, favorite: Boolean): Flow<List<Weather?>>
    suspend fun getCityWeatherForecast(city: String, cityId: Double): Flow<List<WeatherForecast?>>
}