package com.rema.pollutioncontrol.models

import kotlin.math.roundToInt

class AirQualityIndex(var index: Int) {

    fun level(): Int {
        when {
            this.index in 0..50 -> return 1
            this.index in 51..100 -> return 2
            this.index in 101..150 -> return 3
            this.index in 151..200 -> return 4
            this.index in 201..300 -> return 5
            this.index > 300 -> return 6
        }
        return 0
    }

    fun condition(): String {
        when {
            level() == 1 -> return "Excellent"
            level() == 2 -> return "Good"
            level() == 3 -> return "Light"
            level() == 4 -> return "Moderate"
            level() == 5 -> return "Heavy"
            level() == 6 -> return "Severe"
        }
        return "...."
    }

    override fun toString(): String {
        return index.toString()
    }


}