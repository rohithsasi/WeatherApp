package com.canootest.weatherapp.utils

import android.content.Context
import com.canootest.weatherapp.R
import java.text.SimpleDateFormat
import java.util.*

fun Long.toTime(): String {
    //To be rechecked if this is functioning correctly. Assuming this does the conversion for now
    val formatter = SimpleDateFormat("hh:mm")
    val calendar = Calendar.getInstance()
    calendar.timeInMillis = this
    return formatter.format(calendar.time)
}

//Can combine both below to an enum
fun String.description(): String {
    return when (this) {
        "Clouds" -> "Cloudy"
        "Rain" -> "Rainy"
        "Clear" -> "Clear"
        "Sun" -> "Sunny"
        else -> "Clear"
    }
}

fun String.toIcon(context: Context): Int {
    return when (this) {
        "Clouds" -> R.drawable.ic_cloudy
        "Rain" -> R.drawable.ic_rainy
        "Clear" -> R.drawable.ic_sunny
        "Sun" -> R.drawable.ic_sunny
        else -> R.drawable.ic_sunny
    }
}