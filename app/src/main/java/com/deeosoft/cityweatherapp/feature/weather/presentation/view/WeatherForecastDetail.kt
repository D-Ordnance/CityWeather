package com.deeosoft.cityweatherapp.feature.weather.presentation.view

import WeatherText
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.deeosoft.cityweatherapp.feature.weather.domain.entity.Weather
import com.deeosoft.cityweatherapp.feature.weather.presentation.composable.ForecastContentScreen
import com.deeosoft.cityweatherapp.feature.weather.presentation.composable.SavedCityContentScreen
import com.deeosoft.cityweatherapp.feature.weather.presentation.composable.WeatherBox
import com.deeosoft.cityweatherapp.feature.weather.presentation.viewmodel.CityWeatherViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WeatherForecastDetail: ComponentActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val weather = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra("Weather", Weather::class.java)
        } else {
            intent.getParcelableExtra("Weather")
        }
        val cityWeatherViewModel by viewModels<CityWeatherViewModel>()
        weather?.city?.let {
            weather.id.let { it1 ->
                cityWeatherViewModel.getWeatherForecast(it,
                    it1
                )
            }
        }
        setContent {
            Scaffold(
                modifier = Modifier.fillMaxSize(),
                topBar = {
                    Row (Modifier.padding(horizontal = 20.dp)){
                        WeatherText(
                            content = "Forecast Detail",
                            fontWeight = FontWeight.Bold,
                            fontSize = 30.sp
                        )
                    }
                }
            ) {
                if (weather != null) {
                    ForecastDetailMainScreen(Modifier.padding(it), cityWeatherViewModel, weather)
                }
            }
        }
    }
}

@Composable
fun ForecastDetailMainScreen(modifier: Modifier = Modifier, cityWeatherViewModel: CityWeatherViewModel, weather: Weather){
    WeatherBox {
        Column(
            modifier = modifier
                .padding(20.dp)
        ) {
            ForecastContentScreen(context = LocalContext.current, viewModel = cityWeatherViewModel, weather = weather)
        }
    }
}