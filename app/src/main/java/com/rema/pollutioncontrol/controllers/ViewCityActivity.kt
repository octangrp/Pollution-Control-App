package com.rema.pollutioncontrol.controllers

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity;
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.rema.pollutioncontrol.R
import com.rema.pollutioncontrol.models.AirQualityIndex
import com.rema.pollutioncontrol.models.Forest
import com.rema.pollutioncontrol.models.Location
import com.rema.pollutioncontrol.models.Weather

import kotlinx.android.synthetic.main.activity_view_city.*

class ViewCityActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_city)
        (findViewById<View>(R.id.back_button)).setOnClickListener { finish() }
        (findViewById<View>(R.id.weather_icon)).visibility = View.GONE
        val location = intent.getSerializableExtra("location") as Location
        displayWeather(location)
    }

    private fun displayWeather(location: Location) {
        val weather = location.weather
        hideUnusedFields()
        (findViewById<ImageView>(R.id.weather_gauge)).setImageDrawable(getDrawable(weather.gauge()))
        (findViewById<TextView>(R.id.city_name)).text = location.name
        (findViewById<TextView>(R.id.city_condition)).text = weather.qualityIndex.condition()
        (findViewById<TextView>(R.id.aqi_index)).text = weather.qualityIndex.toString()
        (findViewById<TextView>(R.id.humidity)).text = weather.humidityString()
        (findViewById<TextView>(R.id.temperature)).text = weather.temperatureString()
        (findViewById<TextView>(R.id.wind_speed)).text = weather.windSpeedString()
    }

    private fun hideUnusedFields() {
        (findViewById<View>(R.id.weather_icon)).visibility = View.GONE
        (findViewById<View>(R.id.condition) as TextView).visibility = View.GONE
    }

}
