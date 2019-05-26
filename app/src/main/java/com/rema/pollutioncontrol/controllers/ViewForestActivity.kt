package com.rema.pollutioncontrol.controllers

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.rema.pollutioncontrol.R
import com.rema.pollutioncontrol.models.Forest

class ViewForestActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_forest)
        val forest = intent.getSerializableExtra("forest") as Forest
        (findViewById<View>(R.id.back_button)).setOnClickListener { finish() }
        (findViewById<ImageView>(R.id.forest_main_pic)).setImageDrawable(getDrawable(forest.main_pic))
        (findViewById<TextView>(R.id.forest_name)).text = forest.name
        (findViewById<TextView>(R.id.main_temperature)).text = forest.weather.temperatureString()
        (findViewById<TextView>(R.id.weather_condition)).text = forest.weather.conditon
        (findViewById<TextView>(R.id.temperature)).text = forest.weather.temperatureString()
        (findViewById<TextView>(R.id.humidity)).text = forest.weather.humidityString()
        (findViewById<TextView>(R.id.wind_speed)).text = forest.weather.windSpeedString()
        (findViewById<TextView>(R.id.forest_description)).text = forest.description
    }
}
