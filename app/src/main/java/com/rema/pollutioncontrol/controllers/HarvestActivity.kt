package com.rema.pollutioncontrol.controllers

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.View
import android.widget.TextView
import com.rema.pollutioncontrol.R
import com.rema.pollutioncontrol.adapaters.HarvestAdapter
import com.rema.pollutioncontrol.adapaters.PlaceAdapter
import com.rema.pollutioncontrol.models.AirQualityIndex
import com.rema.pollutioncontrol.models.Harvest
import com.rema.pollutioncontrol.models.Location
import com.rema.pollutioncontrol.models.Weather

class HarvestActivity : AppCompatActivity() {
    var harvests = ArrayList<Harvest>()
    lateinit var recyclerView: RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_harvest)
        setUpdateData()
        findViewById<View>(R.id.back_button).setOnClickListener { finish() }
        findViewById<TextView>(R.id.activity_title).text = getString(R.string.crops)
        val linearLayout = LinearLayoutManager(this)
        linearLayout.orientation = RecyclerView.VERTICAL
        recyclerView = findViewById(R.id.recycler_view)
        recyclerView.layoutManager = linearLayout

        val adapter = HarvestAdapter(harvests, this)
        recyclerView.adapter = adapter
    }

    fun setUpdateData() {
        var harvest = Harvest("Potatoes", R.drawable.ic_harvest_potatoes, Weather(AirQualityIndex(30), 40.0, 20.0, 120.0, 2), getString(R.string.nyungwe_description))
        harvest.locations.add(Location("Musanze", "Northern Province"))
        harvest.locations.add(Location("Gicumbi", "Northern Province"))
        harvest.locations.add(Location("Burera", "Northern Province"))
        harvests.add(harvest)


        harvest = Harvest("Sweet Potatoes", R.drawable.ic_harvest_sweet_potatoes, Weather(AirQualityIndex(50), 30.0, 25.0, 130.0, 1), getString(R.string.nyungwe_description))
        harvest.locations.add(Location("Nyagatare", "Eastern Province"))
        harvest.locations.add(Location("Kirehe", "Eastern Province"))
        harvest.locations.add(Location("Rwamagana", "Eastern Province"))
        harvests.add(harvest)

        harvest = Harvest("Bananas", R.drawable.ic_harvest_bananas, Weather(AirQualityIndex(50), 45.0, 19.0, 90.0, 1), getString(R.string.nyungwe_description))
        harvest.locations.add(Location("Kamonyi", "Eastern Province"))
        harvest.locations.add(Location("Muhanga", "Eastern Province"))
        harvest.locations.add(Location("Ruhango", "Eastern Province"))
        harvests.add(harvest)

        harvest = Harvest("Beans", R.drawable.ic_harvest_beans, Weather(AirQualityIndex(50), 30.0, 23.0, 80.0, 1), getString(R.string.nyungwe_description))
        harvest.locations.add(Location("Muhanga", "Eastern Province"))
        harvest.locations.add(Location("Ruhango", "Eastern Province"))
        harvest.locations.add(Location("Nyamagabe", "Eastern Province"))
        harvests.add(harvest)
    }

}
