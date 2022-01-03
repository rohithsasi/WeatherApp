package com.canootest.weatherapp.ui

import android.Manifest
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.canootest.weatherapp.databinding.ActivityMainBinding
import com.canootest.weatherapp.model.WeatherSummary
import com.canootest.weatherapp.viewmodel.WeatherHomeViewModel
import kotlinx.coroutines.*

import androidx.core.app.ActivityCompat

import android.content.pm.PackageManager
import android.location.Address
import android.location.Location
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import android.location.Geocoder
import com.canootest.weatherapp.InMemoryCache
import com.canootest.weatherapp.R
import com.canootest.weatherapp.WeatherApp
import com.canootest.weatherapp.utils.description
import com.canootest.weatherapp.utils.toIcon
import com.canootest.weatherapp.utils.toTime


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: WeatherHomeViewModel
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    private val REQUEST_LOCATION = 1
    var latitude: Double = 0.0
    var longitude: Double = 0.0
    var city = "Torrance,CA"
    val UNIT_IMPERIAL = "imperial"
    val UNIT_METRIC = "metric"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val rootView = binding.root
        setContentView(rootView)
        viewModel = ViewModelProvider(this).get(WeatherHomeViewModel::class.java)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        registerWeatherDataUpdate()
        initClickListeners()
    }

    private fun initClickListeners(){
        binding.forecast.setOnClickListener {
            ForecastActivity.launchActivity(this@MainActivity)
        }

    }

    private fun getLocation() {
        if (ActivityCompat.checkSelfPermission(
                this@MainActivity, Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this@MainActivity, Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                REQUEST_LOCATION
            )
        } else {
            fusedLocationClient.lastLocation
                .addOnSuccessListener { location: Location? ->
                    if (location != null) {
                        latitude = location.latitude
                        longitude = location.longitude

                        //Using a local cache for the forecast activity.
                        InMemoryCache.latitude = latitude
                        InMemoryCache.longitude =  longitude

                        val gCoder = Geocoder(this)
                        val addresses: ArrayList<Address>? =
                            gCoder.getFromLocation(latitude, longitude, 1) as ArrayList<Address>?
                        if (addresses != null && addresses.size > 0) {
                            city = "${addresses[0].locality},${addresses[0].countryCode}"
                        }
                    } else {
                        Toast.makeText(this, "Unable to find location.", Toast.LENGTH_SHORT).show()
                    }
                }

        }
    }

    override fun onStart() {
        super.onStart()
        getLocation()
        viewModel.getWeatherSummary(latitude, longitude, UNIT_IMPERIAL)
    }

    private fun registerWeatherDataUpdate() {
        viewModel.weatherSummaryResult.observe(this, {
            if (it == null) {
                //throwErrorDialog()
            } else {
                updateUi(it)
            }
        })
    }

    private fun updateUi(res: WeatherSummary) {
        //change these to string place holder format
        val degrees = 0x00B0.toChar()
        binding.run {
            weather.setImageResource(res.main.toIcon(this@MainActivity))
            curWeather.text = res.main.description()
            curTemp.text = "${res.temp}$degrees"
            curLocation.text = city
            tempHigh.text = "${res.max}$degrees"
            tempLow.text = "${res.min}$degrees"
            sunset.text = res.sunset.toTime()
            sunsrise.text = res.sunrise.toTime()
            humidity.text = "${res.humidity}%"
        }
        Toast.makeText(WeatherApp.APPLICATION, res.main, Toast.LENGTH_LONG).show()
    }

//    private fun fetchAndUpdateUi() {
//        viewModelResult.observe(this, {
//            when (it) {
//                is OnSuccesResult -> {
//
//                    }
//                }
//                is OnFailureResult -> {
//                    throwErrorDialog()
//                }
//            }
//        })
//    }

}