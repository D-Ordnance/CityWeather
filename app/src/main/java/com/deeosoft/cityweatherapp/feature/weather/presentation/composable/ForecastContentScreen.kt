package com.deeosoft.cityweatherapp.feature.weather.presentation.composable

import WeatherText
import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.layout
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import com.deeosoft.cityweatherapp.R
import com.deeosoft.cityweatherapp.feature.weather.domain.entity.Weather
import com.deeosoft.cityweatherapp.feature.weather.domain.entity.WeatherForecast
import com.deeosoft.cityweatherapp.feature.weather.presentation.view.WeatherForecastDetail
import com.deeosoft.cityweatherapp.feature.weather.presentation.viewmodel.CityWeatherViewModel
import com.deeosoft.cityweatherapp.infastructure.wrapper.UIState

@Composable
fun ForecastContentScreen(context: Context, viewModel: CityWeatherViewModel, weather: Weather) {

    Column(Modifier
        .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally) {
        CurrentWeatherForecast(weather)
        ForeCastDetail(context, viewModel)
    }
}

@Composable
fun CurrentWeatherForecast(weather: Weather){
    val imageIcon = "https://openweathermap.org/img/wn/${weather.icon}@2x.png"
    val degreeSymbol = '\u00B0'
    Row(
        Modifier
            .padding(horizontal = 20.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween) {
        WeatherText(content = weather.city, fontSize = 25.sp, fontWeight = FontWeight.Bold)
        Column(verticalArrangement = Arrangement.spacedBy(10.dp),
            horizontalAlignment = Alignment.End) {
            Row {
                WeatherText(
                    content = "${weather.temp}$degreeSymbol",
                    fontSize = 58.sp,
                    fontWeight = FontWeight.Bold
                )
                AsyncImage(
                    model = ImageRequest
                        .Builder(LocalContext.current)
                        .data(imageIcon)
                        .build(),
                    placeholder = painterResource(R.drawable.weather_placeholder),
                    contentDescription = weather.main
                )
            }
            Row(horizontalArrangement = Arrangement.spacedBy(20.dp)) {
                WeatherText(content = "${weather.humidity}%", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                WeatherText(content = "Today", fontSize = 18.sp, fontWeight = FontWeight.Bold)
            }
        }
    }
}

fun Modifier.parallaxLayoutModifier(scrollState: ScrollState, rate: Int) =
    layout{measurable, constraints ->
        val placeable = measurable.measure(constraints)
        val height = if(rate>0) scrollState.value/rate else scrollState.value
        layout(placeable.width, placeable.height){
            placeable.place(0, height)
        }
    }

@Composable
fun ForeCastDetail(context: Context, viewModel: CityWeatherViewModel){
    val weatherForecastState = viewModel.weatherForecast.observeAsState(initial = UIState.Loading)
    when(weatherForecastState.value) {
        is UIState.Loading -> {}
        is UIState.Error -> {
            val error = (weatherForecastState.value as UIState.Error).message
            Toast.makeText(context, error, Toast.LENGTH_LONG).show()
        }
        is UIState.Success -> {
            val data = (weatherForecastState.value as UIState.Success<List<WeatherForecast?>>).data
            if (data?.isNotEmpty() == true) {
                LazyColumn {
                    items(data.size) {
                        data[it]?.let { it1 ->
                            ForecastItem(
                                weather = it1
                            )
                        }
                    }
                }
            } else {
                EmptyWeatherData(modifier = Modifier)
            }
        }
        else -> {}
    }
}