package com.rema.pollutioncontrol.repository.prefs

import android.content.Context

import com.google.gson.Gson
import com.rema.pollutioncontrol.models.Location
import com.google.gson.reflect.TypeToken


class LocationListPrefs(var context: Context) : SharedPrefs(context) {
    override var key = "LOCATION_LIST_PREFS"
    fun getList(): ArrayList<Location> {
        val json = get(Gson().toJson(ArrayList<Location>()))
        return Gson().fromJson<ArrayList<Location>>(json, object : TypeToken<ArrayList<Location>>() {
        }.type)
    }

    fun saveToList(location: Location) {
        val arrayList = getList()
        if (!isAlreadySaved(arrayList, location)) {
            arrayList.add(location)
            save(Gson().toJson(arrayList))
            return
        }
    }

    private fun isAlreadySaved(locationList: ArrayList<Location>, location: Location): Boolean {
        for (l in locationList) if (l.name == location.name) {
            return true
        }
        return false
    }
}