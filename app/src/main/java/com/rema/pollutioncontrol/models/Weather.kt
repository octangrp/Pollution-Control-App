package com.rema.pollutioncontrol.models

import com.rema.pollutioncontrol.R
import java.io.Serializable
import kotlin.math.roundToInt

class Weather(var qualityIndex: AirQualityIndex, var humidity: Double, var temperature: Double, var windSpeed: Double):Serializable {

    fun temperatureString(): String = temperature.roundToInt().toString() + " \u00B0"+ "C"
    fun windSpeedString(): String = windSpeed.roundToInt().toString() + " km/h"
    fun humidityString(): String = humidity.roundToInt().toString() + " %"

    fun icon(): Int {
        return when (qualityIndex.level()) {
            1 -> R.drawable.ic_weather_excellent
            2 -> R.drawable.ic_weather_good
            3 -> R.drawable.ic_weather_light
            4 -> R.drawable.ic_weather_moderate
            5 -> R.drawable.ic_weather_heavy
            else -> R.drawable.ic_weather_severe
        }
    }

    fun gauge(): Int {
        return when (qualityIndex.level()) {
            1 -> R.drawable.ic_gauge_excellent
            2 -> R.drawable.ic_gauge_good
            3 -> R.drawable.ic_gauge_light
            4 -> R.drawable.ic_gauge_moderate
            5 -> R.drawable.ic_gauge_heavy
            else -> R.drawable.ic_gauge_severe
        }
    }
}