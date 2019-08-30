package com.rema.pollutioncontrol.repository.seeders

import com.rema.pollutioncontrol.R
import com.rema.pollutioncontrol.models.Activity
import com.rema.pollutioncontrol.models.Weather

class ActivitiesDataSeeder : Seeder() {
    override fun run(weather: Weather): ArrayList<Activity> {
        val activities = ArrayList<Activity>()
        activities.add(Activity(R.string.cycling, R.drawable.cycllng, weather, R.string.cycling_description,2, true))
        activities.add(Activity(R.string.running, R.drawable.run, weather, R.string.running_description,1, false))
        activities.add(Activity(R.string.outdoor, R.drawable.ic_activity_outdoor, weather, R.string.outdoor_description,3, true))
        return activities
    }

}