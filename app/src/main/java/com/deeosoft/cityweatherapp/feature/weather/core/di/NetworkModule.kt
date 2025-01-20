package com.deeosoft.cityweatherapp.feature.weather.core.di

import android.content.Context
import com.deeosoft.cityweatherapp.feature.weather.core.network.InternetConnectionService
import com.deeosoft.cityweatherapp.feature.weather.core.network.InternetConnectionServiceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Provides
    @Singleton
    fun provideApplicationContext(@ApplicationContext context: Context): Context {
        return context
    }

    @Provides
    @Singleton
    fun provideNetworkService(context: Context): InternetConnectionService {
        return InternetConnectionServiceImpl(context = context)
    }
}