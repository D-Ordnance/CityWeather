package com.deeosoft.cityweatherapp.feature.weather.presentation.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.deeosoft.cityweatherapp.feature.weather.domain.usecase.FavoriteUseCase
import com.deeosoft.cityweatherapp.feature.weather.domain.usecase.SaveFavoriteStateUseCase
import com.deeosoft.cityweatherapp.feature.weather.domain.usecase.SavedCityUseCase
import com.deeosoft.cityweatherapp.feature.weather.domain.usecase.SearchByCityUseCase
import com.deeosoft.cityweatherapp.feature.weather.domain.usecase.WeatherForecastUseCase
import com.deeosoft.cityweatherapp.util.MainDispatcherRule
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestCoroutineScheduler
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.rules.TestRule
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class CityWeatherViewModelTest {
    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()
    private val dispatcher = StandardTestDispatcher(TestCoroutineScheduler())

    @Mock
    private lateinit var searchByCityUseCase: SearchByCityUseCase
    private lateinit var savedCityUseCase: SavedCityUseCase
    private lateinit var favoriteUseCase: FavoriteUseCase
    private lateinit var saveFavoriteStateUseCase: SaveFavoriteStateUseCase
    private lateinit var weatherForecastUseCase: WeatherForecastUseCase
    private lateinit var viewModel: CityWeatherViewModel

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        viewModel = CityWeatherViewModel(searchByCityUseCase,
            savedCityUseCase,
            favoriteUseCase,
            saveFavoriteStateUseCase,
            weatherForecastUseCase)
    }

    @After
    fun tearDown() {
    }
}