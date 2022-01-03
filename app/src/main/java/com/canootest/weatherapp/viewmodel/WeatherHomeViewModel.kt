package com.canootest.weatherapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.canootest.weatherapp.model.WeatherSummary
import com.canootest.weatherapp.network.model.WeatherData
import com.canootest.weatherapp.repository.WeatherDataRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class WeatherHomeViewModel(application : Application) : AndroidViewModel(application) {
    private val repository by lazy { WeatherDataRepository.get() }

    private val _weatherResult = MutableLiveData<WeatherSummary>()

    val weatherSummaryResult: LiveData<WeatherSummary>
        get()=_weatherResult

    fun getWeatherSummary(lat:Double,lon:Double,unit :String) =viewModelScope.launch {
        var result: WeatherSummary? = null
        withContext(Dispatchers.Default) {
            result = repository.getWeatherDataSummary(lat,lon ,unit)
            if(result == null){
                //handle error or make a result type not nullable with data or the respective error and handle accpordingly in ui
            }
            else{
            _weatherResult.postValue(result)
            }
        }
    }
}