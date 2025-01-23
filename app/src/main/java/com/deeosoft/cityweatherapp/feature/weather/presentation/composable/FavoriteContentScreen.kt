package com.deeosoft.cityweatherapp.feature.weather.presentation.composable

import WeatherItem
import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.deeosoft.cityweatherapp.feature.weather.domain.entity.Weather
import com.deeosoft.cityweatherapp.feature.weather.presentation.view.WeatherForecastDetail
import com.deeosoft.cityweatherapp.feature.weather.presentation.viewmodel.CityWeatherViewModel
import com.deeosoft.cityweatherapp.infastructure.wrapper.UIState

@Composable
fun FavoriteContentScreen(modifier: Modifier = Modifier, context: Context, viewModel: CityWeatherViewModel) {
    val weatherState = viewModel.favoriteState.observeAsState(initial = UIState.Loading)
    var showDialog by remember {
        mutableStateOf(false)
    }
    var idToBeDeleted by remember {
        mutableDoubleStateOf(0.0)
    }
    var itemToBeDeleted by remember {
        mutableStateOf("")
    }

    if(showDialog){
        CityWeatherStrongDialog(
            onConfirm =
            {
                viewModel.saveFavoriteState(idToBeDeleted, false)
                showDialog = false
            },
            onDismiss = { showDialog = false },
            itemToBeDeleted = itemToBeDeleted
        )

    }
    when (weatherState.value) {
        is UIState.Loading -> {}
        is UIState.Error -> {
            val error = (weatherState.value as UIState.Error).message
            Toast.makeText(context, error, Toast.LENGTH_LONG).show()
        }

        is UIState.Success -> {
            val data = (weatherState.value as UIState.Success<List<Weather?>>).data
            if (data?.isNotEmpty() == true) {
                LazyColumn {
                    items(data.size) {
                        data[it]?.let { it1 ->
                            WeatherItem(
                                weather = it1,
                                fromFavorite = true,
                                onTap = {weather ->
                                    val intent = Intent(context, WeatherForecastDetail::class.java)
                                    intent.putExtra("Weather", weather)
                                    context.startActivity(intent)
                                },
                                onFavoriteTap = { state ->
                                    itemToBeDeleted = data[it]?.city.toString()
                                    idToBeDeleted = data[it]?.id!!
                                    showDialog = true
                                }
                            )
                        }
                    }
                }
            } else {
                EmptyWeatherData(modifier = modifier)
            }
        }
        else -> {}

    }
}