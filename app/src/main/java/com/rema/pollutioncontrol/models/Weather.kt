package com.rema.pollutioncontrol.models

import android.content.Context
import com.rema.pollutioncontrol.R
import java.io.Serializable
import kotlin.math.roundToInt

class Weather(var qualityIndex: AirQualityIndex, var humidity: Double, var temperature: Double, var windSpeed: Double, var condition: Int = 1) : Serializable {


    fun temperatureString(): String = temperature.roundToInt().toString() + " \u00B0" + "C"
    fun windSpeedString(): String = windSpeed.roundToInt().toString() + " km/h"
    fun humidityString(): String = humidity.roundToInt().toString() + " %"

    fun icon(): Int {
        return when (this.condition) {
            2 -> R.drawable.ic_weather_cloudy
            3 -> R.drawable.ic_weather_rainny
            else -> R.drawable.ic_weather_sunny
        }
    }

    fun conditionStringResource(): Int {
        return when (this.condition) {
            2 -> R.string.weather_cloudy
            3 -> R.string.weather_raining
            else -> R.string.weather_sunny
        }
    }


}