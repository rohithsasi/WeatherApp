/*
 * RealWear Development Software, Source Code and Object Code.
 * (c) RealWear, Inc. All rights reserved.
 *
 * Contact info@realwear.com for further information about the use of this code.
 */
package com.canootest.weatherapp

/**
 * A local cache to hold app states when alive in memory. This is not permanent and simply a static
 * utility alive as long as app is alive in the background/foreground.
 */
object InMemoryCache {

    var latitude: Double = 0.0
    var longitude: Double = 0.0

    val UNIT_IMPERIAL = "imperial"
    val UNIT_METRIC = "metric"
}