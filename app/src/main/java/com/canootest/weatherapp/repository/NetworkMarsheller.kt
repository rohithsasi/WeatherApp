package com.canootest.weatherapp.repository

import com.canootest.weatherapp.model.WeatherSummary
import com.canootest.weatherapp.network.model.FeelsLike
import com.canootest.weatherapp.network.model.WeatherData
import java.util.concurrent.TimeUnit

import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.roundToInt


internal fun WeatherData.toWeatherSummary(): WeatherSummary {

    return WeatherSummary(
        humidity= this.current.humidity,
        main = this.current.weather[0].main,
        sunrise = this.current.sunrise,
        sunset = this.current.sunset,
        temp = this.current.temp.toInt(),
        //assuming 0th index is current and index 1-8 are next 7 days. Once I finish the task, will come back make sure am reading json correctly
        max = this.daily[0].temp.max.toInt(),
        min = this.daily[0].temp.min.toInt(),
    )
}

internal fun WeatherData.toWeatherForecastSummary(): List<WeatherSummary> {
    var res = mutableListOf<WeatherSummary>()
    for(data in this.daily){
        val item =WeatherSummary(
            humidity= data.humidity,
            main = data.weather[0].main,
            sunset = data.sunset,
            sunrise = data.sunrise,
            temp= data.feels_like.average(), //Could not find a temperature field for foreacast so using the average
            min = data.temp.min.toInt(),
            max = data.temp.max.toInt(),
        )
        res.add(item)
    }
    return  res
}


fun FeelsLike.average():Int{
    return ((this.day+this.eve+this.morn+this.night)/4).roundToInt()
}
