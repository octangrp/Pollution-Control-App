package com.rema.pollutioncontrol.models

class Harvest(var name: Int, var icon: Int, var weather: Weather, var description: String = "") {
    var locations = ArrayList<Location>()
}