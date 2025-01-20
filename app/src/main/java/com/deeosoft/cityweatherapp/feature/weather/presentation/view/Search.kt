package com.deeosoft.cityweatherapp.feature.weather.presentation.view

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.util.Log
import androidx.annotation.RequiresPermission
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import com.deeosoft.cityweatherapp.feature.weather.presentation.composable.CitySearchTextField
import com.deeosoft.cityweatherapp.feature.weather.presentation.composable.SearchContentScreen
import com.deeosoft.cityweatherapp.feature.weather.presentation.composable.WeatherBox
import com.deeosoft.cityweatherapp.feature.weather.presentation.viewmodel.CityWeatherViewModel
import com.google.android.gms.location.FusedLocationProviderClient

@Composable
@RequiresPermission(
    anyOf = [Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION],
)
fun SearchScreen(modifier: Modifier = Modifier,
                 context: Context,
                 cityWeatherViewModel: CityWeatherViewModel,
                 fusedLocationClient: FusedLocationProviderClient){
    WeatherBox {
        Column(
            modifier = modifier
                .padding(20.dp)
        ) {
            CitySearchTextField(modifier = Modifier
                .fillMaxWidth()
                .height(55.dp),
                onSearch = {
                    cityWeatherViewModel.getWeather(it, true)
                    Log.i("City Name:", it)
                },
                onGeoLocate = {
                    if (ActivityCompat.checkSelfPermission(
                            context,
                            Manifest.permission.ACCESS_FINE_LOCATION
                        ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                            context,
                            Manifest.permission.ACCESS_COARSE_LOCATION
                        ) != PackageManager.PERMISSION_GRANTED
                    ) {
                        // TODO: Consider calling
                        //    ActivityCompat#requestPermissions
                        // here to request the missing permissions, and then overriding
                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                        //                                          int[] grantResults)
                        // to handle the case where the user grants the permission. See the documentation
                        // for ActivityCompat#requestPermissions for more details.
                    }
                    fusedLocationClient.lastLocation
                        .addOnSuccessListener { location: Location? ->
                            val lat = location?.latitude
                            val lon = location?.longitude
                        }
                })

            SearchContentScreen(modifier = Modifier
                .fillMaxWidth()
                .weight(1f), context = LocalContext.current, viewModel = cityWeatherViewModel)
        }
    }
}