package com.deeosoft.cityweatherapp.feature.weather.domain.usecase

import com.deeosoft.cityweatherapp.feature.weather.domain.entity.Weather
import com.deeosoft.cityweatherapp.feature.weather.domain.entity.WeatherForecast
import com.deeosoft.cityweatherapp.feature.weather.domain.repository.CityWeatherRepository
import kotlinx.coroutines.flow.Flow

class WeatherForecastUseCase(private val repo: CityWeatherRepository) {
    suspend fun execute(city: String, cityId: Double): Flow<List<WeatherForecast?>> =
        try {
            repo.getCityWeatherForecast(city, cityId)
        }catch (ex: Exception){
            throw ex
        }
}