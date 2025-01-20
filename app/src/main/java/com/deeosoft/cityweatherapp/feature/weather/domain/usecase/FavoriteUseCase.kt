package com.deeosoft.cityweatherapp.feature.weather.domain.usecase

import com.deeosoft.cityweatherapp.feature.weather.domain.entity.Weather
import com.deeosoft.cityweatherapp.feature.weather.domain.repository.CityWeatherRepository
import kotlinx.coroutines.flow.Flow

class FavoriteUseCase(private val repo: CityWeatherRepository) {
    suspend fun execute(): Flow<List<Weather?>> =
        try{
            repo.getFavorite()
        }catch (ex: Exception){
            throw ex
        }

}