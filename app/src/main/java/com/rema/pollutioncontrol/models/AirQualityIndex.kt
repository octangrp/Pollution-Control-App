package com.rema.pollutioncontrol.models

import android.graphics.Color
import com.rema.pollutioncontrol.R
import java.io.Serializable

class AirQualityIndex(var index: Int) : Serializable {

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

    fun conditionStringId(): Int {
        return when {
            level() == 1 -> R.string.excellent
            level() == 2 -> R.string.good
            level() == 3 -> R.string.light
            level() == 4 -> R.string.moderate
            level() == 5 -> R.string.heavy
            level() == 6 -> R.string.severe
            else -> R.string.none
        }
    }

    fun conditionColor(): Int {
        return when {
            level() == 1 -> Color.parseColor("#46A038")
            level() == 2 -> Color.parseColor("#54B948")
            level() == 3 -> Color.parseColor("#FEE900")
            level() == 4 -> Color.parseColor("#F47C20")
            level() == 5 -> Color.parseColor("#DC2826")
            else -> Color.parseColor("#DC2927")
        }
    }

    fun percent(): Int {
        if (index >= 300) {
            return 100
        }
        return (index * 100) / 300
    }

    fun icon(): Int {
        return when (level()) {
            1 -> R.drawable.ic_weather_excellent
            2 -> R.drawable.ic_weather_good
            3 -> R.drawable.ic_weather_light
            4 -> R.drawable.ic_weather_moderate
            5 -> R.drawable.ic_weather_heavy
            else -> R.drawable.ic_weather_severe
        }
    }

    fun gauge(): Int {
        return when (level()) {
            1 -> R.drawable.ic_gauge_excellent
            2 -> R.drawable.ic_gauge_good
            3 -> R.drawable.ic_gauge_light
            4 -> R.drawable.ic_gauge_moderate
            5 -> R.drawable.ic_gauge_heavy
            else -> R.drawable.ic_gauge_severe
        }
    }

    override fun toString(): String {
        return index.toString()
    }


}