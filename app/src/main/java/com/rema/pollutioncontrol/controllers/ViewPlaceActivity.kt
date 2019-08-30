package com.rema.pollutioncontrol.controllers

import android.os.Bundle
import androidx.fragment.app.FragmentTransaction
import androidx.appcompat.app.AppCompatActivity
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.github.mikephil.charting.data.Entry
import com.rema.pollutioncontrol.R
import com.rema.pollutioncontrol.controllers.ui.main.ForecastingLineChartFragment
import com.rema.pollutioncontrol.models.Forecast
import com.rema.pollutioncontrol.models.Place
import com.rema.pollutioncontrol.models.Weather
import com.rema.pollutioncontrol.repository.seeders.ForecastingDataSeeder
import org.joda.time.DateTime
import java.util.ArrayList

class ViewPlaceActivity : AppCompatActivity(), ForecastingLineChartFragment.OnFragmentInteractionListener {

    var weatherForecast = ArrayList<Forecast>()
    lateinit var fragmentTransaction: FragmentTransaction
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_place)
        val place = intent.getSerializableExtra("place") as Place
        (findViewById<View>(R.id.back_button)).setOnClickListener { finish() }
        (findViewById<ImageView>(R.id.forest_main_pic)).setImageDrawable(getDrawable(place.main_pic))
        (findViewById<TextView>(R.id.forest_name)).text = place.name
        displayWeatherDetails(place.weather)
        weatherForecast.add(Forecast(DateTime(), place.weather))
        (findViewById<TextView>(R.id.main_temperature)).text = place.weather.temperatureString(this)
        (findViewById<TextView>(R.id.weather_condition)).text = getString(place.weather.conditionStringResource())
        (findViewById<TextView>(R.id.forest_description)).text = place.description
        weatherForecast.addAll(ForecastingDataSeeder.run(this))
        fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.forecasting_container, ForecastingLineChartFragment.newInstance(weatherForecast))
        fragmentTransaction.commit()
    }

    private fun displayWeatherDetails(weather: Weather) {
        (findViewById<TextView>(R.id.temperature)).text = weather.temperatureString(this)
        (findViewById<TextView>(R.id.humidity)).text = weather.humidityString()
        (findViewById<TextView>(R.id.wind_speed)).text = weather.windSpeedString(this)
        (findViewById<ImageView>(R.id.temperature_icon)).setImageDrawable(getDrawable(weather.icon()))
    }

    override fun onFragmentInteraction(entry: Entry) {
        val forecast = weatherForecast[entry.x.toInt()]
        displayWeatherDetails(forecast.weather)
    }
}
