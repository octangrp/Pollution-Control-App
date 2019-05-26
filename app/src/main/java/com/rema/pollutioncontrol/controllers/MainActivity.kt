package com.rema.pollutioncontrol.controllers

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.rema.pollutioncontrol.models.Activity
import com.rema.pollutioncontrol.adapaters.ActivityListAdapter

import android.view.*
import android.widget.ImageView
import android.widget.TextView
import com.rema.pollutioncontrol.R
import com.rema.pollutioncontrol.models.AirQualityIndex
import com.rema.pollutioncontrol.models.Location
import com.rema.pollutioncontrol.models.Weather
import com.rema.pollutioncontrol.repository.ViewTools


class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private var activities = ArrayList<Activity>()
    private var location: Location? = null
    lateinit var activityRecyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val linearLayout = LinearLayoutManager(this)
        linearLayout.orientation = LinearLayoutManager.HORIZONTAL
        activityRecyclerView = findViewById(R.id.activity_recycler_view)
        activityRecyclerView.layoutManager = linearLayout
        this.getWeather()
        val adapter = ActivityListAdapter(activities, this)
        activityRecyclerView.adapter = adapter

        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)
        val hamMenu = findViewById<View>(R.id.ham_menu) as ImageView
        navView.setNavigationItemSelectedListener(this)
        ViewTools.setNavigationDrawerToggler(hamMenu, drawerLayout)
        val intent =  Intent(this, ViewCityActivity::class.java)
        intent.putExtra("location", this.location)
        (findViewById<View>(R.id.main_view)).setOnClickListener { startActivity(intent) }
    }


    private fun initializeActivities() {
        this.activities.add(Activity(getString(R.string.bicycle), getDrawable(R.drawable.cycllng), 12.0, 23.0))
        this.activities.add(Activity(getString(R.string.running), getDrawable(R.drawable.run), 2.0, 23.0))
        this.activities.add(Activity(getString(R.string.bicycle), getDrawable(R.drawable.cycllng), 12.0, 23.0))
        this.activities.add(Activity("Driving", getDrawable(R.drawable.run), 100.0, 23.0))
        this.activities.add(Activity(getString(R.string.running), getDrawable(R.drawable.run), 2.0, 23.0))
    }

    override fun onBackPressed() {
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when (item.itemId) {
            R.id.nav_home -> {
                // Handle the camera action
            }
            R.id.nav_fight_climate_change -> {
                startActivity(Intent(this, FightClimateChangeActivity::class.java))
            }
            R.id.nav_natural_forest -> {
                startActivity(Intent(this, NaturalForestActivity::class.java))
            }
        }
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    fun getWeather() {
        val weather = Weather(AirQualityIndex(52), 45.0, 21.0, 120.0)
        this.location = Location("Kigali City",weather);
        initializeActivities()
        displayWeather(location)
    }

    fun displayWeather(location: Location?) {
        val weather = location?.weather
        if (weather != null) {
            (findViewById<View>(R.id.weather_icon) as ImageView).setImageDrawable(getDrawable(weather.icon()))
            (findViewById<View>(R.id.condition) as TextView).text = weather.qualityIndex.condition()
            (findViewById<View>(R.id.aqi_index) as TextView).text = weather.qualityIndex.toString()
            (findViewById<View>(R.id.humidity) as TextView).text = weather.humidityString()
            (findViewById<View>(R.id.temperature) as TextView).text = weather.temperatureString()
            (findViewById<View>(R.id.wind_speed) as TextView).text = weather.windSpeedString()
        }
    }

}