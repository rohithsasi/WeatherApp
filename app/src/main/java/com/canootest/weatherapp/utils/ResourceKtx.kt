/*
 * RealWear Development Software, Source Code and Object Code.
 * (c) RealWear, Inc. All rights reserved.
 *
 * Contact info@realwear.com for further information about the use of this code.
 */

package com.canootest.weatherapp.utils

import com.canootest.weatherapp.WeatherApp

/**
 * Extension function to get string from resId.
 */
fun Int.getString(): String {
    return WeatherApp.APPLICATION.getString(this)
}

/**
 * Extension function to get string from resId with params.
 */
fun Int.getString(param: String): String {
    return WeatherApp.APPLICATION.getString(this, param)
}