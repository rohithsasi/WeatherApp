package com.canootest.weatherapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.canootest.weatherapp.model.WeatherSummary
import com.canootest.weatherapp.repository.WeatherDataRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ForecastViewModel(application: Application) : AndroidViewModel(application) {
    private val repository by lazy { WeatherDataRepository.get() }

    private val _weatherForecast = MutableLiveData<List<WeatherSummary>>()

    val weatherForecast: LiveData<List<WeatherSummary>>
        get() = _weatherForecast

    fun getWeatherSummary(lat: Double, lon: Double, unit: String) = viewModelScope.launch {
        var result: List<WeatherSummary>? = null
        withContext(Dispatchers.Default) {
            result = repository.getWeatherForecastSummary(lat, lon, unit)
            if (result == null) {
                //handle error or make a result type not nullable with data or the respective error and handle accpordingly in ui
            } else {
                _weatherForecast.postValue(result)
            }
        }
    }
}