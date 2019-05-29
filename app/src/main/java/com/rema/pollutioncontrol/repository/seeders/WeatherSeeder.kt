package com.rema.pollutioncontrol.repository.seeders

import com.rema.pollutioncontrol.models.AirQualityIndex
import com.rema.pollutioncontrol.models.Weather

class WeatherSeeder {
    companion object {
        fun run(): Weather {
            return Weather(AirQualityIndex((10..350).random()), ((10..100).random()).toDouble(), ((12..30).random()).toDouble(), ((60..150).random()).toDouble(), (1..3).random())
        }
    }

}