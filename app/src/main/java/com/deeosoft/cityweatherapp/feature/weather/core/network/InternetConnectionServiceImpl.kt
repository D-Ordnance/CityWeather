package com.deeosoft.cityweatherapp.feature.weather.core.network

import android.content.Context
import android.net.ConnectivityManager

class InternetConnectionServiceImpl(private val context: Context): InternetConnectionService {
    override suspend fun hasInternetConnection(): Boolean {
        var haveConnectedWifi = false
        var haveConnectedMobile = false

        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
        val netInfo = cm!!.allNetworkInfo
        for (ni in netInfo) {
            if (ni.typeName.equals(
                    "WIFI",
                    ignoreCase = true
                )
            ) if (ni.isConnected) haveConnectedWifi = true
            if (ni.typeName.equals(
                    "MOBILE",
                    ignoreCase = true
                )
            ) if (ni.isConnected) haveConnectedMobile = true
        }
        return haveConnectedWifi || haveConnectedMobile

    }
}