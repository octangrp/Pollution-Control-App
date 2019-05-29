package com.rema.pollutioncontrol.controllers

import android.os.Bundle
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import com.rema.pollutioncontrol.R
import com.rema.pollutioncontrol.controllers.ui.main.SectionsPagerAdapter
import com.rema.pollutioncontrol.models.AirQualityIndex
import com.rema.pollutioncontrol.models.Location
import com.rema.pollutioncontrol.models.Weather

class TabbedActivity : AppCompatActivity() {
    var locations = ArrayList<Location>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        this.setupData()
        val sectionsPagerAdapter = SectionsPagerAdapter(this, supportFragmentManager, locations)
        val viewPager: ViewPager = findViewById(R.id.view_pager)
        viewPager.adapter = sectionsPagerAdapter
    }


    fun setupData() {
        locations.add(Location("Muhanga", Weather(AirQualityIndex(20), 50.0, 19.0, 96.0, 2)))
        locations.add(Location("Huye", Weather(AirQualityIndex(120), 40.0, 28.0, 86.0, 1)))
        locations.add(Location("Gicumbi", Weather(AirQualityIndex(160), 60.0, 25.0, 78.0, 3)))
        locations.add(Location("Rubavu", Weather(AirQualityIndex(210), 30.0, 29.0, 110.0, 2)))
        locations.add(Location("Nyamata", Weather(AirQualityIndex(370), 20.0, 32.0, 70.0, 1)))
    }
}