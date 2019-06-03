package com.rema.pollutioncontrol.repository.prefs.units

import android.content.Context
import com.rema.pollutioncontrol.repository.prefs.Prefs
import kotlin.math.roundToInt

class TemperaturePref(context: Context) : UnitPrefs, Prefs(context) {
    override var key = "APP_TEMPERATURE_UNIT"

    override fun convert(value: Double): String {
        return switchUnit(value).roundToInt().toString() + " " + getUnitString()
    }

    private fun getUnit(): Int {
        return if (get().isNotEmpty() && get().toInt() == 2) {
            2
        } else {
            1
        }
    }

    override fun switchUnit(value: Double): Double {
        if (getUnit() == 2) {
            return (value * 9 / 5) + 32
        }
        return value
    }

    override fun getUnitString(): String {
        return if (getUnit() == 2) {
            " \u00B0" + "F"
        } else {
            " \u00B0" + "C"
        }
    }
}