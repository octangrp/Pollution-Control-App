package com.rema.pollutioncontrol.models

import android.content.Context
import android.graphics.Color
import com.rema.pollutioncontrol.R
import java.io.Serializable
import kotlin.math.roundToInt

class Weather(var qualityIndex: AirQualityIndex, var humidity: Double, var temperature: Double, var windSpeed: Double, var conditon: String? = "") : Serializable {


    fun temperatureString(): String = temperature.roundToInt().toString() + " \u00B0" + "C"
    fun windSpeedString(): String = windSpeed.roundToInt().toString() + " km/h"
    fun humidityString(): String = humidity.roundToInt().toString() + " %"

    fun icon(context: Context): Int {
        return when (this.conditon) {
            context.getString(R.string.weather_cloudy) -> R.drawable.ic_weather_cloudy
            context.getString(R.string.weather_raining) -> R.drawable.ic_weather_rainny
            else -> R.drawable.ic_weather_sunny
        }
    }

}