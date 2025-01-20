package com.deeosoft.cityweatherapp.feature.weather.data.repository

import com.deeosoft.cityweatherapp.feature.weather.core.exception.ServerException
import com.deeosoft.cityweatherapp.feature.weather.data.datasource.CityWeatherDataSource
import com.deeosoft.cityweatherapp.feature.weather.domain.entity.Weather
import com.deeosoft.cityweatherapp.feature.weather.domain.entity.WeatherForecast
import com.deeosoft.cityweatherapp.feature.weather.domain.repository.CityWeatherRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import kotlin.jvm.Throws

class CityWeatherRepositoryImpl @Inject constructor(
    private val dataSource: CityWeatherDataSource
): CityWeatherRepository {
    override suspend fun searchByCity(city: String, forceServer: Boolean): Flow<List<Weather?>> =
        flow {
            try {
                val response: List<Weather?>
                if (!forceServer) {
                    response = dataSource.localSource(city)
                    emit(response)
                }
                val remoteResponse: List<Weather?> = dataSource.remoteSource(city)
                emit(remoteResponse)
            }catch(ex: Throwable){
                throw ex
            }
        }

    override suspend fun getSavedCity(): Flow<List<Weather?>> =
        flow {
            try{
                val response = dataSource.getAllSavedCityWeather()
                emit(response)
            }catch (ex: Exception){
                throw ex
            }
        }

    override suspend fun getFavorite(): Flow<List<Weather?>> =
        flow {
            try {
                val response = dataSource.getAllFavoriteWeather()
                emit(response)
            }catch (ex: Exception){
                throw ex
            }
        }

    override suspend fun saveFavoriteState(id: Double, state: Boolean): Flow<List<Weather?>> =
        flow {
            try {
                dataSource.saveFavoriteState(id, state)
                val response = dataSource.getAllFavoriteWeather()
                emit(response)
            }catch (ex: Exception){
                throw ex
            }
        }

    override suspend fun getCityWeatherForecast(city: String, cityId: Double): Flow<List<WeatherForecast?>> =
        flow {
            try {
                val response = dataSource.getWeatherForecast(city, cityId)
                emit(response)
            }catch (ex: Exception){
                throw ex
            }
        }
}