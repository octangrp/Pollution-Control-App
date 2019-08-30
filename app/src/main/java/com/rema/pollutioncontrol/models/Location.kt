package com.rema.pollutioncontrol.models

import com.rema.pollutioncontrol.repository.seeders.ActivitiesDataSeeder
import java.io.Serializable

class Location(var name: String, var weather: Weather? = null, var isCurrent: Boolean = false) : Serializable {
    var activities = ArrayList<Activity>()
    var province: String = ""


    init {
        setActivities()
    }

    fun setActivities() {
        if (this.weather != null) {
            this.activities = (ActivitiesDataSeeder()).run(this.weather!!)
        }
    }

    constructor(name: String, province: String) : this(name) {
        this.province = province
    }


}