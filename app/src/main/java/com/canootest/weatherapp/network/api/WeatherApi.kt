package com.canootest.weatherapp.network.api

import android.util.Log
import com.canootest.weatherapp.network.WeatherRestClient
import com.canootest.weatherapp.network.model.WeatherData
import com.canootest.weatherapp.network.webservice.WeatherServiceApi

/**
 * Api layer responsible for the network request
 */
internal interface WeatherApi {


    suspend fun getWeatherData(lat:Double,long: Double, unit :String): WeatherData?

    companion object {
        fun get(): WeatherApi {
            return WeatherApiImpl
        }
    }
}

/**
 * Rest client
 */
private val WEATHER_SERVICE_API: WeatherServiceApi by lazy {
    WeatherRestClient.get().weatherServiceApi
}

internal object WeatherApiImpl : WeatherApi {

    override suspend fun getWeatherData(lat:Double,long: Double,unit:String): WeatherData? {
        try {
            return WEATHER_SERVICE_API.fetchWeatherData(lat,long, unit=unit)
        }
        catch (ex :Exception){
            //Handle error and propagate up to the ui if needed
            Log.d("Weather fetch Exception","${ex.message}")
        }
        return null
    }

}