package com.deeosoft.cityweatherapp.feature.weather.presentation.composable

import WeatherText
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.deeosoft.cityweatherapp.R

@Composable
fun CitySearchTextField(modifier: Modifier = Modifier, onSearch: (String) -> Unit, onGeoLocate: () -> Unit){
    val controller = LocalSoftwareKeyboardController.current;
    var city by remember { mutableStateOf("") }
    var myColor = colorResource(id =  R.color.skyBlue1)
    return TextField(modifier = modifier,
        value = city,
        placeholder = { Text("Enter City") },
        textStyle = TextStyle.Default.copy(fontSize = 20.sp),
        leadingIcon = {Icon(painter = painterResource(id = R.drawable.location),
            contentDescription = "City", tint = myColor)},
        trailingIcon = {
            Box(Modifier.clickable { onGeoLocate() }) {
                Icon(painter = painterResource(id = R.drawable.search_location),
                    contentDescription = "Location", tint = myColor)
            }
        },
        singleLine = true,
        keyboardActions = KeyboardActions { controller?.hide()
            onSearch(city) },
        colors = TextFieldDefaults.colors(unfocusedTextColor = Color.Black,
            focusedTextColor = Color.Black,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            focusedContainerColor = Color.White,
            unfocusedContainerColor = Color.White),
        shape = RoundedCornerShape(25.dp),
        onValueChange = { city = it })
}

@Composable
fun WeatherBox(content: @Composable BoxScope.() -> Unit){
    val gradient = Brush.verticalGradient(
        0.0f to colorResource(id = R.color.skyBlue1),
        500.0f to colorResource(R.color.skyBlue2))
    Box (
        Modifier
            .fillMaxSize()
            .background(gradient),
        content = content)
}

@Composable
fun EmptyWeatherData(modifier: Modifier){
    return Box(modifier = modifier,
        contentAlignment = Alignment.Center) {
        Column (verticalArrangement = Arrangement.spacedBy(12.dp)){
            Icon(painter = painterResource(id = R.drawable.no_data),
                contentDescription = "No Weather Data",
                tint = Color.Unspecified)
            WeatherText(content = "No data At this moment")
        }
    }
}