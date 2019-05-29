package com.rema.pollutioncontrol.controllers

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.design.widget.TabLayout
import android.support.v4.view.GravityCompat
import android.support.v4.view.ViewPager
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.rema.pollutioncontrol.R
import com.rema.pollutioncontrol.controllers.ui.main.SectionsPagerAdapter
import com.rema.pollutioncontrol.models.AirQualityIndex
import com.rema.pollutioncontrol.models.Location
import com.rema.pollutioncontrol.models.Weather
import com.rema.pollutioncontrol.repository.LocationListPrefs
import com.rema.pollutioncontrol.repository.ViewTools
import com.rema.pollutioncontrol.repository.seeders.WeatherSeeder


class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    var locations = ArrayList<Location>()
    lateinit var activityTitle: TextView
    lateinit var sectionsPagerAdapter: SectionsPagerAdapter
    lateinit var viewPager: ViewPager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        this.setupData()
        activityTitle = findViewById(R.id.activity_title)
        sectionsPagerAdapter = SectionsPagerAdapter(this, supportFragmentManager, locations)
        viewPager = findViewById(R.id.view_pager)
        viewPager.adapter = sectionsPagerAdapter
        val tabLayout = findViewById<TabLayout>(R.id.tabDots)
        tabLayout.setupWithViewPager(viewPager, true)
        activityTitle.text = locations[0].name
        viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

            }

            override fun onPageSelected(position: Int) {
                activityTitle.text = locations[position].name
            }

            override fun onPageScrollStateChanged(state: Int) {

            }
        })
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)
        val hamMenu = findViewById<View>(R.id.ham_menu) as ImageView
        navView.setNavigationItemSelectedListener(this)
        ViewTools.setNavigationDrawerToggler(hamMenu, drawerLayout)
        (findViewById<ImageView>(R.id.ham_menu_add)).setOnClickListener { startActivity(Intent(this, SearchActivity::class.java)) }
//        this.addListener()
    }

    override fun onResume() {
        super.onResume()
        this.setupData()
        sectionsPagerAdapter.locations = locations
        sectionsPagerAdapter.notifyDataSetChanged()
    }

    private fun initializeActivities(weather: Weather) {
//        this.activities.add(Activity(getString(R.string.bicycle), getDrawable(R.drawable.cycllng), weather, 2, true))
//        this.activities.add(Activity(getString(R.string.running), getDrawable(R.drawable.run), weather, 1, false))
//        this.activities.add(Activity(getString(R.string.bicycle), getDrawable(R.drawable.cycllng), weather, 3, true))
//        this.activities.add(Activity("Driving", getDrawable(R.drawable.run), weather, 4, true))
//        this.activities.add(Activity(getString(R.string.running), getDrawable(R.drawable.run), weather, 2, true))
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
            R.id.places -> {
                startActivity(Intent(this, PlacesActivity::class.java))
            }
        }
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    private fun setupData() {
        locations = ArrayList<Location>()
        locations.add(Location("Kigali City", WeatherSeeder.run()))
        val savedLocationList = LocationListPrefs(this).getList()
        for (location in savedLocationList) {
            location.weather = WeatherSeeder.run()
            location.setActivities()
            locations.add(location)
        }
    }


}