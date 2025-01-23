package com.deeosoft.cityweatherapp.feature.weather.data.datasource

import android.database.sqlite.SQLiteConstraintException
import com.deeosoft.cityweatherapp.feature.weather.core.exception.CacheException
import com.deeosoft.cityweatherapp.feature.weather.core.exception.InternetConnectionException
import com.deeosoft.cityweatherapp.feature.weather.core.exception.ServerException
import com.deeosoft.cityweatherapp.feature.weather.core.helper.CityWeatherDatabase
import com.deeosoft.cityweatherapp.feature.weather.core.network.APIService
import com.deeosoft.cityweatherapp.feature.weather.core.network.InternetConnectionService
import com.deeosoft.cityweatherapp.feature.weather.domain.entity.ServerForecastWeather
import com.deeosoft.cityweatherapp.feature.weather.domain.entity.ServerWeather
import com.deeosoft.cityweatherapp.feature.weather.domain.entity.Weather
import com.deeosoft.cityweatherapp.feature.weather.domain.entity.WeatherForecast
import javax.inject.Inject
import kotlin.jvm.Throws

class CityWeatherDataSourceImpl @Inject constructor(
    private val internetService: InternetConnectionService,
    private val database: CityWeatherDatabase,
    private val apiService: APIService
): CityWeatherDataSource {

    override suspend fun remoteSource(city: String): List<Weather?> =
        if(internetService.hasInternetConnection()) {
            try {
                val response = apiService.getWeather(city)
                if (response != null) {
                    saveCityWeather(ServerWeather.fromJson(response))
                    getLocalCityWeather(city)
                } else {
                    throw CacheException("Cache error")
                }
            } catch (ex: Exception) {
                ex.printStackTrace()
                if(ex is SQLiteConstraintException){
                    getLocalCityWeather(city)
                }else{
                    throw ServerException(ex.message!!)
                }
            }
        }else{
            val errorWithData = getLocalCityWeather(city)
            throw InternetConnectionException("No Internet Connection", errorWithData)
        }

    override suspend fun localSource(city: String): List<Weather?> =
        try {
            val response = database.weatherDao().getAllWeather()
            response
        }catch (ex: Exception){
            throw throwCacheException(ex, "No new Update")
        }

    private suspend fun getLocalCityWeather(city: String): List<Weather?> =
        try {
            val list = mutableListOf<Weather?>()
            val response = database.weatherDao().getWeather(city)
            list.add(response)
            list
        }catch (ex: Exception){
            throw throwCacheException(ex, "Record exist before")
        }

    private suspend fun saveCityWeather(data: ServerWeather) {
        val weather = query(data.id)
        if(weather != null){
            database.weatherDao().updateFromServer(data.id,
                data.main,
                data.description,
                data.icon,
                data.temp,
                data.tempMin,
                data.tempMax,
                data.humidity)
        }else{
            database.weatherDao().insert(Weather.fromServerWeather(data))
        }
    }

    private suspend fun query(id: Double): Weather = database.weatherDao().query(id)

    private fun throwCacheException(ex: Exception, cacheMessage: String): Throwable{
        if(ex is SQLiteConstraintException){
            throw CacheException(cacheMessage)
        }else{
            throw CacheException("An Error Occurred")
        }
    }

    override suspend fun getAllSavedCityWeather(): List<Weather?> =
        try {
            val response = database.weatherDao().getAllWeather()
            response
        }catch (ex: Exception) {
            throw throwCacheException(ex, "No new Update")
        }

    override suspend fun getAllFavoriteWeather(): List<Weather?> =
        try {
            val response = database.weatherDao().getAllFavorite()
            response
        }catch (ex: Exception) {
            throw throwCacheException(ex, "No new Update")
        }

    override suspend fun saveFavoriteState(id: Double, state: Boolean) =
        try {
            database.weatherDao().updateFavorite(id, state)
        }catch (ex: Exception) {
            throw throwCacheException(ex, "No new Update")
        }

    private suspend fun saveWeatherForecast(forecast: List<WeatherForecast>) =
        try {
            database.weatherDao().insert(forecast)
        }catch (ex: Exception) {
            throw throwCacheException(ex, "No new Update")
        }

    override suspend fun getWeatherForecast(city: String, cityId: Double): List<WeatherForecast?> =
        if(internetService.hasInternetConnection()) {
            try {
                val response = apiService.getWeatherForecast(city)
                if (response != null) {
                    val serverForecastWeather = ServerForecastWeather.fromJson(response)
                    saveWeatherForecast(WeatherForecast.fromServerWeatherForecast(cityId, serverForecastWeather))
                    getLocalWeatherForecast(cityId)
                } else {
                    throw CacheException("Cache error")
                }
            } catch (ex: Exception) {
                ex.printStackTrace()
                if(ex is SQLiteConstraintException){
                    getLocalWeatherForecast(cityId)
                }else{
                    throw ServerException(ex.message!!)
                }
            }
        }else{
            throw InternetConnectionException("No Internet Connection", "")
        }

    private suspend fun getLocalWeatherForecast(id: Double): List<WeatherForecast?> =
        try {
            val response = database.weatherDao().getWeatherForeCast(id)
            response
        }catch (ex: Exception){
            throw throwCacheException(ex, "Record exist before")
        }

}