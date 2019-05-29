package com.rema.pollutioncontrol.controllers

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import com.rema.pollutioncontrol.R
import com.rema.pollutioncontrol.adapaters.PlaceAdapter
import com.rema.pollutioncontrol.models.AirQualityIndex
import com.rema.pollutioncontrol.models.Place
import com.rema.pollutioncontrol.models.Weather
import java.util.ArrayList

class PlacesActivity : AppCompatActivity() {

    var placeList = ArrayList<Place>()
    lateinit var recyclerView: RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)
        val linearLayout = LinearLayoutManager(this)
        linearLayout.orientation = LinearLayoutManager.VERTICAL
        recyclerView = findViewById(R.id.recycler_view)
        recyclerView.layoutManager = linearLayout
        (findViewById<View>(R.id.back_button)).setOnClickListener { finish() }
        (findViewById<View>(R.id.activity_title) as TextView).text = getString(R.string.places)
        initializeList()
        val adapter = PlaceAdapter(placeList, this)
        recyclerView.adapter = adapter

    }

    private fun initializeList() {
        placeList.add(Place(
                R.drawable.forest_nyungwe_thumb,
                R.drawable.forest_nyungwe_main,
                "Nyungwe Place",
                Weather(
                        AirQualityIndex(10),
                        45.0,
                        20.0,
                        126.0,
                        1
                ), getString(R.string.nyungwe_description)
        )
        )
        placeList.add(Place(
                R.drawable.forest_gishwati_thumb,
                R.drawable.forest_gishwati_main,
                "Gishwati Place",
                Weather(
                        AirQualityIndex(10),
                        45.0,
                        23.0,
                        112.0,
                        2
                ), getString(R.string.large_text)
        )
        )
        placeList.add(Place(
                R.drawable.forest_bisoke_thumb,
                R.drawable.forest_bisoke_main,
                "Bisoke Place",
                Weather(
                        AirQualityIndex(10),
                        60.0,
                        16.0,
                        96.0,
                        3
                ), getString(R.string.large_text)
        )
        )
    }
}
