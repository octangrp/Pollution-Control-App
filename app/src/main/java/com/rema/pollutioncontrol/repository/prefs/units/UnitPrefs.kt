package com.rema.pollutioncontrol.repository.prefs.units

interface UnitPrefs {
    fun convert(value: Double):String
    fun switchUnit(value: Double):Double
    fun getUnitString(): String

}