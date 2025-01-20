package com.deeosoft.cityweatherapp.feature.weather.presentation.view

import WeatherText
import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.List
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.deeosoft.cityweatherapp.feature.weather.presentation.composable.BottomNavItem
import com.deeosoft.cityweatherapp.feature.weather.presentation.viewmodel.CityWeatherViewModel
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        val cityWeatherViewModel by viewModels<CityWeatherViewModel>()
        setContent {
            MainScreen(context = LocalContext.current, cityWeatherViewModel = cityWeatherViewModel, fusedLocationClient = fusedLocationClient)
        }
    }

    @Composable
    fun MainScreen(modifier: Modifier = Modifier,
                   context: Context,
                   cityWeatherViewModel: CityWeatherViewModel,
                   fusedLocationClient: FusedLocationProviderClient){
        val bottomNavItems = listOf(
            BottomNavItem(title = "My Search", label = "Search", Icons.Rounded.Search),
            BottomNavItem(title ="Recent Visit", label = "Recent Visit", Icons.Rounded.List),
            BottomNavItem(title = "My Favorite", label = "Favorite", Icons.Rounded.Favorite),
        )
        var selectedIndex: Int by remember {
            mutableIntStateOf(0)
        }
        Scaffold(
            modifier = modifier.fillMaxSize(),
            topBar = {
                Row (Modifier.padding(horizontal = 20.dp)){
                    WeatherText(
                        content = bottomNavItems[selectedIndex].title,
                        fontWeight = FontWeight.Bold,
                        fontSize = 30.sp
                    )
                }
            },
            bottomBar = {
                NavigationBar {
                    bottomNavItems.forEachIndexed { index, bottomNavItem ->
                        NavigationBarItem(
                            selected = selectedIndex == index,
                            onClick = { selectedIndex = index },
                            icon = {
                                Icon(imageVector = bottomNavItem.icon, contentDescription = bottomNavItem.label)
                            },
                            label = {
                                WeatherText(content = bottomNavItem.label,
                                    fontSize = 15.sp,
                                    fontWeight = FontWeight.Normal)
                            }
                        )
                    }
                }
            }
        ) {
            ContentScreen(modifier = Modifier.padding(it), context, selectedIndex, cityWeatherViewModel, fusedLocationClient)
        }
    }

    @Composable
    fun ContentScreen(modifier: Modifier = Modifier,
                      context: Context,
                      selectedIndex: Int,
                      cityWeatherViewModel: CityWeatherViewModel,
                      fusedLocationClient: FusedLocationProviderClient){
        when(selectedIndex){
            0-> SearchScreen(modifier = modifier, context, cityWeatherViewModel = cityWeatherViewModel, fusedLocationClient = fusedLocationClient)
            1-> SavedCityWeatherScreen(modifier = modifier, cityWeatherViewModel = cityWeatherViewModel)
            2-> FavoriteScreen(modifier = modifier, cityWeatherViewModel = cityWeatherViewModel)
        }
    }
}