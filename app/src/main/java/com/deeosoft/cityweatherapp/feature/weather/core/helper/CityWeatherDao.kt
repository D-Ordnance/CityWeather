package com.deeosoft.cityweatherapp.feature.weather.core.helper

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.deeosoft.cityweatherapp.feature.weather.domain.entity.Weather
import com.deeosoft.cityweatherapp.feature.weather.domain.entity.WeatherForecast

@Dao
interface CityWeatherDao {
    @Query("SELECT * from Weather ORDER BY id DESC")
    suspend fun getAllWeather(): List<Weather?>

    @Query("SELECT * from Weather WHERE city = :city")
    suspend fun getWeather(city: String): Weather

    @Query("SELECT * from WeatherForecast WHERE cityId = :id")
    suspend fun getWeatherForeCast(id: Double): List<WeatherForecast>

    @Query("UPDATE Weather SET main = :main, " +
            "description = :description," +
            " icon = :icon, " +
            "`temp` = :temp, " +
            "tempMin = :tempMin, " +
            "tempMax = :tempMax, " +
            "humidity = :humidity " +
            "WHERE id = :id")
    suspend fun updateFromServer(id: Double,
                       main: String,
                       description: String,
                       icon: String,
                       temp: Double,
                       tempMin: Double,
                       tempMax: Double,
                       humidity: Double): Int

    @Query("UPDATE Weather SET favorite = :favorite WHERE id = :id")
    suspend fun updateFavorite(id: Double, favorite: Boolean)

    @Query("SELECT * From Weather WHERE id = :id")
    suspend fun query(id: Double) : Weather

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: Weather)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertWeather(item: List<Weather>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: List<WeatherForecast>)

    @Query("SELECT * from Weather Where favorite = true ORDER BY id DESC")
    suspend fun getAllFavorite(): List<Weather?>
}