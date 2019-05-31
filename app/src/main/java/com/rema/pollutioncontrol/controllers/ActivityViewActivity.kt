package com.rema.pollutioncontrol.controllers

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.CardView
import android.widget.ImageView
import android.widget.TextView
import com.rema.pollutioncontrol.R
import com.rema.pollutioncontrol.models.Activity

class ActivityViewActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view)
        findViewById<ImageView>(R.id.back_button).setOnClickListener { finish() }
        val activity = intent.getSerializableExtra("activity") as Activity
        displayActivity(activity)
    }

    private fun displayActivity(activity: Activity) {
        findViewById<ImageView>(R.id.activity_icon).setImageDrawable(getDrawable(activity.icon))
        findViewById<TextView>(R.id.activity_name).text = getString(activity.name)
        findViewById<CardView>(R.id.shape).setCardBackgroundColor(resources.getColor(activity.badgeColor()))
        findViewById<TextView>(R.id.condition).text = getString(activity.condition())

        findViewById<TextView>(R.id.humidity).text = activity.weather.humidityString()
        findViewById<TextView>(R.id.temperature).text = activity.weather.temperatureString()
        findViewById<TextView>(R.id.wind_speed).text = activity.weather.windSpeedString()

        findViewById<TextView>(R.id.description).text = getString(activity.description)
    }
}
