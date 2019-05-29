package com.rema.pollutioncontrol.repository.seeders

import com.rema.pollutioncontrol.R
import com.rema.pollutioncontrol.models.Activity
import com.rema.pollutioncontrol.models.Weather

class ActivitiesDataSeeder : Seeder() {
    override fun run(weather: Weather): ArrayList<Activity> {
        val activities = ArrayList<Activity>()
        activities.add(Activity(R.string.bicycle, R.drawable.cycllng, weather, 2, true))
        activities.add(Activity(R.string.running, R.drawable.run, weather, 1, false))
        activities.add(Activity(R.string.bicycle, R.drawable.cycllng, weather, 3, true))
        activities.add(Activity(R.string.bicycle, R.drawable.run, weather, 4, true))
        activities.add(Activity(R.string.running, R.drawable.run, weather, 2, true))
        return activities
    }

}