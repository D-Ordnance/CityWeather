package com.deeosoft.cityweatherapp.feature.weather.domain.entity

import android.os.Parcel
import android.os.Parcelable
import android.util.Log
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

data class ServerWeather(
    val id: Double,
    val city: String,
    val main: String,
    val description: String,
    val icon: String,
    val temp: Double,
    val tempMin: Double,
    val tempMax: Double,
    val humidity: Double
){
    companion object {
        fun fromJson (map: Map<String, Any>): ServerWeather {
            Log.i("HERE", map.toString())
            val weather: List<Map<String, Any>> = map["weather"] as List<Map<String, Any>>
            val id = map["id"] as Double
            val city = map["name"] as String
            val main = weather[0]["main"] as String
            val description = weather[0]["description"] as String
            val icon = weather[0]["icon"] as String
            val mainObject = map["main"] as Map<String, Any>
            val temp = mainObject["temp"] as Double
            val tempMin = mainObject["temp_min"] as Double
            val tempMax = mainObject["temp_max"] as Double
            val humidity = mainObject["humidity"] as Double
            return ServerWeather(id, city, main, description, icon, temp, tempMin, tempMax, humidity)
        }
    }
}

@Entity(tableName = "Weather",
    indices = [
        Index(
            value = ["id", "city"],
            unique = true)]
)
data class Weather(
    @PrimaryKey val id: Double,
    val city: String,
    val main: String,
    val description: String,
    val icon: String,
    val temp: Double,
    val tempMin: Double,
    val tempMax: Double,
    val humidity: Double,
    val favorite: Boolean
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readDouble(),
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readDouble(),
        parcel.readDouble(),
        parcel.readDouble(),
        parcel.readDouble(),
        parcel.readByte() != 0.toByte()
    )
    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeDouble(id)
        parcel.writeString(city)
        parcel.writeString(main)
        parcel.writeString(description)
        parcel.writeString(icon)
        parcel.writeDouble(temp)
        parcel.writeDouble(tempMin)
        parcel.writeDouble(tempMax)
        parcel.writeDouble(humidity)
        parcel.writeByte(if (favorite) 1 else 0)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Weather> {
        override fun createFromParcel(parcel: Parcel): Weather {
            return Weather(parcel)
        }

        override fun newArray(size: Int): Array<Weather?> {
            return arrayOfNulls(size)
        }

        fun fromServerWeather(serverWeather: ServerWeather): Weather =
            Weather(
                id = serverWeather.id,
                city = serverWeather.city,
                main = serverWeather.main,
                description = serverWeather.description,
                icon = serverWeather.icon,
                temp = serverWeather.temp,
                tempMin = serverWeather.tempMin,
                tempMax = serverWeather.tempMax,
                humidity = serverWeather.humidity,
                favorite = false)
    }
}
