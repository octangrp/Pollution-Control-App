package com.rema.pollutioncontrol.controllers

import android.content.Intent
import android.os.Bundle
import com.google.android.material.navigation.NavigationView
import com.google.android.material.tabs.TabLayout
import androidx.core.view.GravityCompat
import androidx.viewpager.widget.ViewPager
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.rema.pollutioncontrol.R
import com.rema.pollutioncontrol.adapaters.ActivityListAdapter
import com.rema.pollutioncontrol.controllers.ui.main.SectionsPagerAdapter
import com.rema.pollutioncontrol.models.Activity
import com.rema.pollutioncontrol.models.AirQualityIndex
import com.rema.pollutioncontrol.models.Location
import com.rema.pollutioncontrol.models.Weather
import com.rema.pollutioncontrol.repository.LocationListPrefs
import com.rema.pollutioncontrol.repository.ViewTools
import com.rema.pollutioncontrol.repository.seeders.WeatherSeeder


class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener, ActivityListAdapter.ItemClickListener {

    var locations = ArrayList<Location>()
    lateinit var locationPin: ImageView
    lateinit var activityTitle: TextView
    lateinit var sectionsPagerAdapter: SectionsPagerAdapter
    lateinit var viewPager: ViewPager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        this.setupData()
        activityTitle = findViewById(R.id.activity_title)
        locationPin = findViewById(R.id.current_location_pin)
        sectionsPagerAdapter = SectionsPagerAdapter(this, supportFragmentManager, locations)
        viewPager = findViewById(R.id.view_pager)
        viewPager.adapter = sectionsPagerAdapter
        val tabLayout = findViewById<TabLayout>(R.id.tabDots)
        tabLayout.setupWithViewPager(viewPager, true)
        activityTitle.text = locations[0].name
        toggleLocationPin(locations[0])
        viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

            }

            override fun onPageSelected(position: Int) {
                activityTitle.text = locations[position].name
                toggleLocationPin(locations[position])
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
    }

    private fun toggleLocationPin(location: Location) {
        if (location.isCurrent) {
            locationPin.visibility = View.VISIBLE
        } else {
            locationPin.visibility = View.GONE
        }
    }

    override fun onResume() {
        super.onResume()
        this.setupData()
        sectionsPagerAdapter.locations = locations
        sectionsPagerAdapter.notifyDataSetChanged()
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
            R.id.nav_agriculture -> {
                startActivity(Intent(this, HarvestActivity::class.java))
            }
        }
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    private fun setupData() {
        locations = ArrayList<Location>()
        locations.add(Location("Kigali City", WeatherSeeder.run(), true))
        val savedLocationList = LocationListPrefs(this).getList()
        for (location in savedLocationList) {
            location.weather = WeatherSeeder.run()
            location.setActivities()
            locations.add(location)
        }
    }

    override fun onItemClicked(activity: Activity) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}