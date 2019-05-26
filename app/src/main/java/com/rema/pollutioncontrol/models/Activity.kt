package com.rema.pollutioncontrol.models

import android.graphics.drawable.Drawable
import kotlin.math.roundToInt

class Activity(var name: String, var icon: Drawable?, var speed: Double, var temperature: Double) {

    fun speedString(): String {
        return speed.roundToInt().toString() + " km/h"
    }

    fun temperatureString(): String {
        return temperature.roundToInt().toString() + " \u00B0" + "C"
    }

}
