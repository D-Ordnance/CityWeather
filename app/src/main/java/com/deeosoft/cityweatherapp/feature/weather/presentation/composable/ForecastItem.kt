package com.deeosoft.cityweatherapp.feature.weather.presentation.composable

import WeatherText
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import com.deeosoft.cityweatherapp.R
import com.deeosoft.cityweatherapp.feature.weather.domain.entity.Weather
import com.deeosoft.cityweatherapp.feature.weather.domain.entity.WeatherForecast

@Composable
fun ForecastItem(weather: WeatherForecast){
    val imageIcon = "https://openweathermap.org/img/wn/${weather.icon}@2x.png"
    val degreeSymbol = '\u00B0'
    return Card(
        Modifier
            .fillMaxWidth()
            .padding(top = 20.dp)
            .background(Color.Unspecified),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        )
    ) {
        Column(
            Modifier
                .padding(horizontal = 15.dp, vertical = 10.dp)
                .background(Color.White),
            verticalArrangement = Arrangement.spacedBy(12.dp)) {
            Row {
                Row(Modifier.weight(1f)) {
                    Column {
                        WeatherText(
                            content = weather.temp.toString() + degreeSymbol,
                            fontSize = 30.sp,
                            fontWeight = FontWeight.Bold
                        )
                        WeatherText(
                            content = weather.description,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Light
                        )
                    }
                    AsyncImage(
                        model = ImageRequest
                            .Builder(LocalContext.current)
                            .data(imageIcon)
                            .build(),
                        placeholder = painterResource(R.drawable.weather_placeholder),
                        contentDescription = "${weather.cityId}"
                    )
                }

            }
            Row(verticalAlignment = Alignment.Bottom) {
                WeatherText(
                    Modifier.weight(1f),
                    content = weather.day,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Normal
                )
                Column(
                    Modifier.weight(1f),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    WeatherText(
                        content = "${weather.humidity}%",
                        textAlign = TextAlign.Center,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Normal
                    )
                    WeatherText(
                        content = "Humidity",
                        fontSize = 8.sp,
                        fontWeight = FontWeight.Normal
                    )
                }
                WeatherText(
                    Modifier.weight(1f),
                    textAlign = TextAlign.End,
                    content = "${weather.tempMin}$degreeSymbol - ${weather.tempMax}$degreeSymbol",
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Normal
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun WeatherItemPreview() {
    ForecastItem(mockWeather)
}

val mockWeather = WeatherForecast(
    dt = 572879787.0,
    date = "20-09-2025 00:00:00",
    cityId = 909.0,
    day = "Monday",
    description = "Cloudy",
    icon = "04d",
    temp = 30.6,
    tempMin = 29.8,
    tempMax = 31.9,
    humidity = 67.0,)