package com.rema.pollutioncontrol.controllers

import android.os.Bundle
import androidx.fragment.app.FragmentTransaction
import androidx.appcompat.app.AppCompatActivity
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.rema.pollutioncontrol.R
import com.rema.pollutioncontrol.models.Location
import com.github.mikephil.charting.data.*
import com.rema.pollutioncontrol.controllers.ui.main.ForecastingLineChartFragment
import com.rema.pollutioncontrol.controllers.ui.main.PieChartFragment
import com.rema.pollutioncontrol.models.AirQualityIndex
import com.rema.pollutioncontrol.models.Forecast
import com.rema.pollutioncontrol.models.Weather
import com.rema.pollutioncontrol.repository.seeders.ForecastingDataSeeder
import org.joda.time.DateTime
import kotlin.collections.ArrayList


class ViewCityActivity : AppCompatActivity(), ForecastingLineChartFragment.OnFragmentInteractionListener {


    lateinit var location: Location
    var airQualityIndex: Int = 0
    var pollutionCondition: String = ""
    var pollutionConditionColor: Int = 0
    var weatherForecast = ArrayList<Forecast>()
    lateinit var fragmentTransaction: FragmentTransaction
    lateinit var pieChartFragment: PieChartFragment
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_city)
        (findViewById<View>(R.id.back_button)).setOnClickListener { finish() }
        (findViewById<View>(R.id.weather_icon)).visibility = View.GONE
        location = intent.getSerializableExtra("location") as Location
        displayWeather(location)
        location.weather?.let { Forecast(DateTime(), it) }?.let { weatherForecast.add(it) }
        location.weather?.qualityIndex?.index?.let { updateQualityIndex(it) }
        setWeatherForecasting()
        fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.forecasting_container, ForecastingLineChartFragment.newInstance(weatherForecast))
        location.weather?.qualityIndex?.let { pieChartFragment = PieChartFragment.newInstance(it) }?.let { fragmentTransaction.replace(R.id.pie_chart_container, pieChartFragment) }
        fragmentTransaction.commit()
        showCurrentLocationPin()
    }

    private fun showCurrentLocationPin() {
        if (location.isCurrent) {
            val activityTitleIcon: ImageView = findViewById(R.id.activity_title_icon)
            activityTitleIcon.setImageDrawable(getDrawable(R.drawable.ic_location))
            activityTitleIcon.visibility = View.VISIBLE
        }
    }

    private fun displayWeather(location: Location) {
        val weather: Weather? = location.weather
        hideUnusedFields()
        (findViewById<TextView>(R.id.activity_title)).text = location.name
        if (weather != null) {
            displayWeatherDetails(weather)
        }
    }

    private fun displayWeatherDetails(weather: Weather) {
        (findViewById<TextView>(R.id.aqi_index)).text = weather.qualityIndex.toString()
        (findViewById<TextView>(R.id.humidity)).text = weather.humidityString()
        (findViewById<TextView>(R.id.temperature)).text = weather.temperatureString(this)
        (findViewById<ImageView>(R.id.temperature_icon)).setImageDrawable(getDrawable(weather.icon()))
        (findViewById<TextView>(R.id.wind_speed)).text = weather.windSpeedString(this)
    }

    private fun hideUnusedFields() {
        (findViewById<View>(R.id.weather_icon)).visibility = View.GONE
        (findViewById<View>(R.id.condition) as TextView).visibility = View.GONE
    }

    fun setWeatherForecasting() {
        weatherForecast.addAll(ForecastingDataSeeder.run(this))
    }

    override fun onFragmentInteraction(entry: Entry) {
        val forecast = weatherForecast[entry.x.toInt()]
        updateQualityIndex(forecast.weather.qualityIndex.index)
        displayWeatherDetails(forecast.weather)
        pieChartFragment.updateChart(forecast.weather.qualityIndex)
    }

    fun updateQualityIndex(index: Int) {
        val qualityIndex = AirQualityIndex(index)
        airQualityIndex = qualityIndex.percent()
        pollutionCondition = getString(qualityIndex.conditionStringId())
        pollutionConditionColor = qualityIndex.conditionColor()
    }

}
