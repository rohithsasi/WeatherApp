package com.canootest.weatherapp.repository

import android.util.Log
import com.canootest.weatherapp.model.WeatherSummary
import com.canootest.weatherapp.network.api.WeatherApi

/**
 * Repository that fetches network data and emits results processed for ui to the view model
 */
interface WeatherDataRepository {
    suspend fun getWeatherDataSummary(lat: Double, lon: Double, unit: String): WeatherSummary?
    suspend fun getWeatherForecastSummary(
        lat: Double,
        lon: Double,
        unit: String
    ): List<WeatherSummary>?

    companion object {
        fun get(): WeatherDataRepository {
            return WeatherDataRepositoryImpl
        }
    }
}

internal object WeatherDataRepositoryImpl : WeatherDataRepository {
    private var weather: WeatherApi = WeatherApi.get()
    override suspend fun getWeatherDataSummary(
        lat: Double,
        lon: Double,
        unit: String
    ): WeatherSummary? {
        val res = weather.getWeatherData(lat, lon, unit)?.toWeatherSummary()
        return res
    }

    override suspend fun getWeatherForecastSummary(
        lat: Double,
        lon: Double,
        unit: String
    ): List<WeatherSummary>? {
        val res = weather.getWeatherData(lat, lon, unit)?.toWeatherForecastSummary()
        return res
    }
}