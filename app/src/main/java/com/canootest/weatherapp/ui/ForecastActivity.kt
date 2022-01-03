package com.canootest.weatherapp.ui

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.canootest.weatherapp.InMemoryCache
import com.canootest.weatherapp.databinding.ActivityForecastBinding
import com.canootest.weatherapp.viewmodel.ForecastViewModel
import com.canootest.weatherapp.viewmodel.WeatherHomeViewModel
import androidx.recyclerview.widget.DividerItemDecoration

class ForecastActivity : AppCompatActivity() {

    private lateinit var binding: ActivityForecastBinding
    private lateinit var forecastAdapter: ForecastAdapter
    private lateinit var viewModel: ForecastViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityForecastBinding.inflate(layoutInflater)
        val rootView = binding.root
        setContentView(rootView)

        forecastAdapter = ForecastAdapter()
        binding.weatherList.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = forecastAdapter
        }


        binding.weatherList.apply {
            val dividerItemDecoration = DividerItemDecoration(
                this.getContext(),
                LinearLayoutManager.VERTICAL
            )
            this.addItemDecoration(dividerItemDecoration)
        }
        viewModel = ViewModelProvider(this).get(ForecastViewModel::class.java)
        registerWeatherDataUpdate()
    }

    override fun onStart() {
        super.onStart()
        InMemoryCache.apply {
            viewModel.getWeatherSummary(latitude, longitude, UNIT_IMPERIAL)
        }
    }

    private fun registerWeatherDataUpdate() {
        viewModel.weatherForecast.observe(this, {
            if (it == null) {
                //throwErrorDialog()
            } else {

                forecastAdapter.updateItems(it)

            }
        })
    }

    companion object {
        fun launchActivity(context: Context) {
            context.startActivity(Intent(context, ForecastActivity::class.java))
        }
    }
}