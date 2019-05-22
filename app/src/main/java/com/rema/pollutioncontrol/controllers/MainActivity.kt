package com.rema.pollutioncontrol.controllers

import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.rema.pollutioncontrol.models.Activity
import com.rema.pollutioncontrol.R
import com.rema.pollutioncontrol.adapaters.ActivityListAdapter

import android.view.*
import android.widget.ImageView
import android.widget.TextView
import com.rema.pollutioncontrol.models.AirQualityIndex
import com.rema.pollutioncontrol.models.Weather
import com.rema.pollutioncontrol.repository.ViewTools
import kotlin.math.roundToInt


class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    var activities = ArrayList<Activity>()
    lateinit var activityRecyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.rema.pollutioncontrol.R.layout.activity_main)
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

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main2, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when (item.itemId) {
            R.id.nav_home -> {
                // Handle the camera action
            }

        }
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    fun getWeather() {
        val weather = Weather(AirQualityIndex(52), 45.0, 21.0, 120.0)
        initializeActivities()
        displayWeather(weather)
    }

    fun displayWeather(weather: Weather) {
        val weather_icon = findViewById<View>(R.id.weather_icon) as ImageView
        val condition = findViewById<View>(R.id.condition) as TextView
        val aqi_index = findViewById<View>(R.id.aqi_index) as TextView
        val humidity = findViewById<View>(R.id.humidity) as TextView
        val temperature = findViewById<View>(R.id.temperature) as TextView
        val wind_speed = findViewById<View>(R.id.wind_speed) as TextView
        weather_icon.setImageDrawable(getDrawable(weather.icon()))
        condition.text = weather.qualityIndex.condition()
        aqi_index.text = weather.qualityIndex.toString()
        humidity.text = weather.humidityString()
        temperature.text = weather.temperatureString()
        wind_speed.text = weather.windSpeedString()
    }

}