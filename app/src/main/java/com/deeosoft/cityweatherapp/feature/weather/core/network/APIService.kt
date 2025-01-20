package com.deeosoft.cityweatherapp.feature.weather.core.network

import retrofit2.http.GET
import retrofit2.http.Query

interface APIService {
    @GET("weather")
    suspend fun getWeather(
        @Query("q") city: String,
        @Query("appid") key: String = "5e7f4563b27cbb83bdd4f633a79ef9b8",
        @Query("units") units: String = "metric"
    ): Map<String, Any>

    @GET("forecast")
    suspend fun getWeatherForecast(
        @Query("q") city: String,
        @Query("appid") key: String = "5e7f4563b27cbb83bdd4f633a79ef9b8",
        @Query("units") units: String = "metric"
    ): Map<String, Any>
}