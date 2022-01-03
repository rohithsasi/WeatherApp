package com.canootest.weatherapp.utils

import android.app.Application
import com.facebook.stetho.Stetho

object StethoUtils {
    /**
     * Initializes stetho
     *
     * @param application used for the context
     */
    fun init(application: Application) {
        Stetho.initializeWithDefaults(application)
    }

}