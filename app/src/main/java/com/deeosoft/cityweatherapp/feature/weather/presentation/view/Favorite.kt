package com.deeosoft.cityweatherapp.feature.weather.presentation.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.deeosoft.cityweatherapp.feature.weather.presentation.composable.FavoriteContentScreen
import com.deeosoft.cityweatherapp.feature.weather.presentation.composable.WeatherBox
import com.deeosoft.cityweatherapp.feature.weather.presentation.viewmodel.CityWeatherViewModel

@Composable
fun FavoriteScreen(modifier: Modifier = Modifier, cityWeatherViewModel: CityWeatherViewModel){
    cityWeatherViewModel.getFavorite()
    WeatherBox {
        Column(
            modifier = modifier
                .padding(20.dp)
        ) {
            FavoriteContentScreen(modifier = Modifier
                .fillMaxWidth()
                .weight(1f), context = LocalContext.current, viewModel = cityWeatherViewModel)
        }
    }
}