package com.deeosoft.cityweatherapp.feature.weather.domain.usecase

import com.deeosoft.cityweatherapp.feature.weather.domain.entity.Weather
import com.deeosoft.cityweatherapp.feature.weather.domain.repository.CityWeatherRepository
import kotlinx.coroutines.flow.Flow

class SearchByCityUseCase(private val repo: CityWeatherRepository) {
    suspend fun execute(city: String, forceServer: Boolean): Flow<List<Weather?>> =
        try {
            repo.searchByCity(city, forceServer)
        }catch (ex: Exception){
            throw ex
        }
}