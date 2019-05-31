package com.rema.pollutioncontrol.models

class Harvest(var name: String, var icon: Int, var weather: Weather, var description: String = "") {
    var locations = ArrayList<Location>()
}