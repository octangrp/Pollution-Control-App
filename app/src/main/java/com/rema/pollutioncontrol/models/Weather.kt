package com.rema.pollutioncontrol.models

import android.graphics.drawable.Drawable
import com.rema.pollutioncontrol.R
import kotlin.math.roundToInt

class Weather(var qualityIndex: AirQualityIndex, var humidity: Double, var temperature: Double, var windSpeed: Double) {

    fun temperatureString(): String = temperature.roundToInt().toString() + "\u2103"
    fun windSpeedString(): String = windSpeed.roundToInt().toString() + " km/h"
    fun humidityString(): String = humidity.roundToInt().toString() + " %"

    fun icon(): Int {
        when (qualityIndex.level()) {
            1 -> return R.drawable.ic_weather_excellent
            2 -> return R.drawable.ic_weather_good
            3 -> return R.drawable.ic_weather_light
            4 -> return R.drawable.ic_weather_moderate
            5 -> return R.drawable.ic_weather_heavy
            6 -> return R.drawable.ic_weather_severe
        }
        return R.drawable.ic_weather_severe
    }
}