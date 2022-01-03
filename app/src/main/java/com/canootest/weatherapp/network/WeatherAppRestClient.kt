package com.canootest.weatherapp.network

import com.canootest.weatherapp.network.webservice.WeatherServiceApi
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Rest client, interceptors and adapters
 */
internal interface WeatherRestClient {
    val weatherServiceApi: WeatherServiceApi

    companion object {
        fun get(): WeatherRestClient {
            return WeatherRestClientImpl
        }
    }
}

private object WeatherRestClientImpl : WeatherRestClient {

    override val weatherServiceApi: WeatherServiceApi
        get() {
            return getWeatherApi()
        }

    private var weather: WeatherServiceApi? = null

    private fun getWeatherApi(): WeatherServiceApi =  weather ?: weatherApiBuilder(WeatherServiceApi::class.java).apply { weather = this }


    private fun <T> weatherApiBuilder(clz: Class<T>, baseUrl: String = "https://api.openweathermap.org"): T {
        val gson = GsonBuilder()
            .setLenient()
            .create()

        return Retrofit.Builder()
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .baseUrl(baseUrl)
            .client(okHttpClient)
            .build()
            .create(clz)
    }

    private fun getHttpClient(): OkHttpClient {
        return OkHttpClient.Builder().build()

    }

    private val okHttpClient: OkHttpClient
        get() {
            return getHttpClient()
        }
}
