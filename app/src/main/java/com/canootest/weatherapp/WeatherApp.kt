package com.canootest.weatherapp

import android.app.Application
import com.canootest.weatherapp.utils.StethoUtils

class WeatherApp: Application() {

    override fun onCreate() {
        super.onCreate()
        application = this
        initStetho()
    }

    private fun initStetho() {
        //inspecting traffic along with android profiler for debug purposes
        StethoUtils.init(this)
    }

    companion object {
        private lateinit var application: Application
        val APPLICATION by lazy { application }
    }
}