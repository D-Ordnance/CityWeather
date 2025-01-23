package com.deeosoft.cityweatherapp.feature.weather.core.exception

class InternetConnectionException(message: String, val data: Any): Exception(message){
    inline fun <reified T> getOfflineData(): T? = data as T
}