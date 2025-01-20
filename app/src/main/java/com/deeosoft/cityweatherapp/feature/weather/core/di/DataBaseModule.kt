package com.deeosoft.cityweatherapp.feature.weather.core.di

import android.content.Context
import androidx.room.Room
import com.deeosoft.cityweatherapp.feature.weather.core.helper.CityWeatherDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun provideDataBaseObject(context: Context): CityWeatherDatabase {
        return Room.databaseBuilder(
            context,
            CityWeatherDatabase::class.java,
            "city_weather"
        ).build()
    }
}