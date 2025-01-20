package com.deeosoft.cityweatherapp.feature.weather.core.di

import com.deeosoft.cityweatherapp.feature.weather.core.helper.CityWeatherDatabase
import com.deeosoft.cityweatherapp.feature.weather.core.network.APIService
import com.deeosoft.cityweatherapp.feature.weather.core.network.InternetConnectionService
import com.deeosoft.cityweatherapp.feature.weather.data.datasource.CityWeatherDataSource
import com.deeosoft.cityweatherapp.feature.weather.data.datasource.CityWeatherDataSourceImpl
import com.deeosoft.cityweatherapp.feature.weather.data.repository.CityWeatherRepositoryImpl
import com.deeosoft.cityweatherapp.feature.weather.domain.repository.CityWeatherRepository
import com.deeosoft.cityweatherapp.feature.weather.domain.usecase.FavoriteUseCase
import com.deeosoft.cityweatherapp.feature.weather.domain.usecase.SaveFavoriteStateUseCase
import com.deeosoft.cityweatherapp.feature.weather.domain.usecase.SavedCityUseCase
import com.deeosoft.cityweatherapp.feature.weather.domain.usecase.SearchByCityUseCase
import com.deeosoft.cityweatherapp.feature.weather.domain.usecase.WeatherForecastUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://api.openweathermap.org/data/2.5/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideAPIService(retrofit: Retrofit): APIService {
        return retrofit.create(APIService::class.java)
    }

    @Provides
    @Singleton
    fun provideWeatherDataSource(internetService: InternetConnectionService, db: CityWeatherDatabase, apiService: APIService): CityWeatherDataSource {
        return CityWeatherDataSourceImpl(internetService, db, apiService)
    }

    @Provides
    @Singleton
    fun provideWeatherRepository(dataSource: CityWeatherDataSource): CityWeatherRepository {
        return CityWeatherRepositoryImpl(dataSource)
    }

    @Provides
    @Singleton
    fun provideWeatherUseCase(repository: CityWeatherRepository): SearchByCityUseCase {
        return SearchByCityUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideSavedCityWeatherUseCase(repository: CityWeatherRepository): SavedCityUseCase {
        return SavedCityUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideFavoriteUseCase(repository: CityWeatherRepository): FavoriteUseCase {
        return FavoriteUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideFavoriteStateUseCase(repository: CityWeatherRepository): SaveFavoriteStateUseCase {
        return SaveFavoriteStateUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideWeatherForecastUseCase(repository: CityWeatherRepository): WeatherForecastUseCase {
        return WeatherForecastUseCase(repository)
    }
}