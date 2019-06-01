package com.rema.pollutioncontrol.controllers

import android.os.Bundle
import androidx.fragment.app.FragmentTransaction
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import android.widget.ImageView
import android.widget.TextView
import com.github.mikephil.charting.data.Entry
import com.rema.pollutioncontrol.R
import com.rema.pollutioncontrol.controllers.ui.main.ForecastingLineChartFragment
import com.rema.pollutioncontrol.models.Activity
import com.rema.pollutioncontrol.models.Forecast
import com.rema.pollutioncontrol.repository.seeders.ForecastingDataSeeder
import org.joda.time.DateTime

class ActivityViewActivity : AppCompatActivity(), ForecastingLineChartFragment.OnFragmentInteractionListener {
    lateinit var activity: Activity
    var forecasting = ArrayList<Forecast>()
    lateinit var fragmentTransaction: FragmentTransaction
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view)
        findViewById<ImageView>(R.id.back_button).setOnClickListener { finish() }
        activity = intent.getSerializableExtra("activity") as Activity
        forecasting.add(Forecast(DateTime(), activity.weather))
        forecasting.addAll(ForecastingDataSeeder.run(this))
        displayActivity()
        fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.forecasting_container, ForecastingLineChartFragment.newInstance(forecasting))
        fragmentTransaction.commit()
    }


    private fun displayActivity() {
        findViewById<ImageView>(R.id.activity_icon).setImageDrawable(getDrawable(activity.icon))
        findViewById<TextView>(R.id.activity_name).text = getString(activity.name)
        updateCondition()
        findViewById<TextView>(R.id.humidity).text = activity.weather.humidityString()
        findViewById<TextView>(R.id.temperature).text = activity.weather.temperatureString()
        findViewById<TextView>(R.id.wind_speed).text = activity.weather.windSpeedString()

        findViewById<TextView>(R.id.description).text = getString(activity.description)
    }

    private fun updateCondition() {
        findViewById<CardView>(R.id.shape).setCardBackgroundColor(resources.getColor(activity.badgeColor()))
        findViewById<TextView>(R.id.condition).text = getString(activity.condition())
    }

    override fun onFragmentInteraction(entry: Entry) {
        activity.weather = forecasting[entry.x.toInt()].weather
        updateCondition()

    }


}
