package com.rema.pollutioncontrol.repository.seeders

import android.content.Context
import com.rema.pollutioncontrol.models.Activity
import com.rema.pollutioncontrol.models.Weather

abstract class Seeder {
    abstract fun run(weather: Weather): ArrayList<Activity>
}