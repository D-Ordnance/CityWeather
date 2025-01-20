package com.deeosoft.cityweatherapp.feature.weather.domain.entity

import android.util.Log
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import java.text.SimpleDateFormat

data class ServerForecastWeatherItem(
    val dt: Double,
    val date: String,
    val description: String,
    val icon: String,
    val temp: Double,
    val tempMin: Double,
    val tempMax: Double,
    val humidity: Double
)

data class ServerForecastWeather(
    val forecast: List<ServerForecastWeatherItem>
){
    companion object {
        fun fromJson (map: Map<String, Any>): ServerForecastWeather {
            Log.i("HERE", map.toString())
            val weatherForecast: List<Map<String, Any>> = map["list"] as List<Map<String, Any>>
            val firstOccurrences = weatherForecast
                .groupBy { it["dt_txt"]?.toString()?.substring(0, 10) } // Group by dt_txt (yyyy-MM-dd)
                .mapValues { it.value.first() } // Pick the first entry for each date
                .values.toList()
            val forecast = mutableListOf<ServerForecastWeatherItem>()
            firstOccurrences.forEach {
                val dt = it["dt"] as Double
                val date = it["dt_txt"] as String
                val main = it["main"] as Map<String, Any>
                val temp = main["temp"] as Double
                val tempMin = main["temp_min"] as Double
                val tempMax = main["temp_max"] as Double
                val humidity = main["humidity"] as Double
                val weather = it["weather"] as List<Map<String, Any>>
                val description = weather[0]["description"] as String
                val icon = weather[0]["icon"] as String
                val forecastItem = ServerForecastWeatherItem(
                    dt = dt,
                    date= date,
                    description = description,
                    icon = icon,
                    temp = temp,
                    tempMin = tempMin,
                    tempMax = tempMax,
                    humidity = humidity
                )
                forecast.add(forecastItem)
            }
            return ServerForecastWeather(forecast)
        }
    }
}

@Entity(tableName = "WeatherForecast",
    indices = [
        Index(
            value = ["dt"],
            unique = true)]
)
data class WeatherForecast(
    @PrimaryKey val dt: Double,
    val date: String,
    val cityId: Double,
    val day: String,
    val description: String,
    val icon: String,
    val temp: Double,
    val tempMin: Double,
    val tempMax: Double,
    val humidity: Double
){
    companion object {
        fun fromServerWeatherForecast(cityId: Double, serverForecastWeather: ServerForecastWeather): List<WeatherForecast> {
            val forecast = mutableListOf<WeatherForecast>()
            serverForecastWeather.forecast.forEach {
                val parser =  SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                val formatter = SimpleDateFormat("EEEE")
                val day = formatter.format(parser.parse(it.date))
                forecast.add(
                    WeatherForecast(
                        dt = it.dt,
                        date = it.date,
                        cityId = cityId,
                        day = day,
                        description = it.description,
                        icon = it.icon,
                        temp = it.temp,
                        tempMin = it.tempMin,
                        tempMax = it.tempMax,
                        humidity = it.humidity)
                )
            }
            return forecast
        }

    }
}
