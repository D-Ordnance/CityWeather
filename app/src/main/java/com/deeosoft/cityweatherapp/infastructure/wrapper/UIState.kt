package com.deeosoft.cityweatherapp.infastructure.wrapper

sealed class UIState<out T>{
    data object Loading: UIState<Nothing>()
    data class Success<out T>(val data: T?): UIState<T>()
    data class Error<out T>(val message: String, val data: T? = null): UIState<T>()
}