package com.rema.pollutioncontrol.controllers

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.View
import android.widget.TextView
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.utils.MPPointF
import com.rema.pollutioncontrol.R
import com.rema.pollutioncontrol.adapaters.PlaceAdapter
import com.rema.pollutioncontrol.models.AirQualityIndex
import com.rema.pollutioncontrol.models.Forecast
import com.rema.pollutioncontrol.models.Place
import com.rema.pollutioncontrol.models.Weather
import com.rema.pollutioncontrol.repository.seeders.ForecastingDataSeeder
import java.util.ArrayList

class PlacesActivity : AppCompatActivity() {

    var placeList = ArrayList<Place>()
    lateinit var recyclerView: RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)
        val linearLayout = LinearLayoutManager(this)
        linearLayout.orientation = RecyclerView.VERTICAL
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
                getString(R.string.nyungwe_park),
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
                getString(R.string.akagera_name),
                Weather(
                        AirQualityIndex(10),
                        45.0,
                        23.0,
                        112.0,
                        2
                ), getString(R.string.akagera_description)
        )
        )
        placeList.add(Place(
                R.drawable.forest_bisoke_thumb,
                R.drawable.forest_bisoke_main,
                getString(R.string.volcanoes_park_name),
                Weather(
                        AirQualityIndex(10),
                        60.0,
                        16.0,
                        96.0,
                        3
                ), getString(R.string.volcanoes_park_description)
        )
        )
    }


}
