package com.deeosoft.cityweatherapp.feature.weather.domain.usecase

import com.deeosoft.cityweatherapp.feature.weather.domain.entity.Weather
import com.deeosoft.cityweatherapp.feature.weather.domain.repository.CityWeatherRepository
import kotlinx.coroutines.flow.Flow

class SaveFavoriteStateUseCase(private val repo: CityWeatherRepository) {
    suspend fun execute(id: Double, favorite: Boolean): Flow<List<Weather?>> =
        try {
            repo.saveFavoriteState(id, favorite)
        }catch (ex: Exception){
            throw ex
        }
}