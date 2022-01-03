package com.canootest.weatherapp.network.webservice

import com.canootest.weatherapp.network.model.WeatherData
import retrofit2.http.GET
import retrofit2.http.Query

const val GAMES = "data/2.5/onecall"

interface WeatherServiceApi {
    @GET(GAMES)
    suspend fun fetchWeatherData(
        @Query("lat") latitude: Double,
        @Query("lon") longitude: Double,
        @Query("exclude") exclude: String ="hourly,minutely",
        @Query("appid") appId: String ="d8074f59d10a03fd22ec1b5ceacea820",
        @Query("units") unit: String
    ): WeatherData
}