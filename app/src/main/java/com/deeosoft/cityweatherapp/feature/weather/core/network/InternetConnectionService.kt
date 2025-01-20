package com.deeosoft.cityweatherapp.feature.weather.core.network

interface InternetConnectionService {
    suspend fun hasInternetConnection(): Boolean
}