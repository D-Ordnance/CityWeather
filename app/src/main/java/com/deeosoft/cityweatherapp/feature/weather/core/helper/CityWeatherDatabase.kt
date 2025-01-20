package com.deeosoft.cityweatherapp.feature.weather.core.helper

import androidx.room.Database
import androidx.room.RoomDatabase
import com.deeosoft.cityweatherapp.feature.weather.domain.entity.Weather
import com.deeosoft.cityweatherapp.feature.weather.domain.entity.WeatherForecast

@Database(
    entities = [Weather::class, WeatherForecast::class],
    version = 1,
)
abstract class CityWeatherDatabase : RoomDatabase() {
    abstract fun weatherDao(): CityWeatherDao
}