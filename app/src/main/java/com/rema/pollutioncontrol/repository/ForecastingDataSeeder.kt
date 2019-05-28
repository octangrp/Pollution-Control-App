package com.rema.pollutioncontrol.repository

import android.content.Context
import com.rema.pollutioncontrol.R
import com.rema.pollutioncontrol.models.AirQualityIndex
import com.rema.pollutioncontrol.models.Forecast
import com.rema.pollutioncontrol.models.Weather
import org.joda.time.DateTime
import kotlin.collections.ArrayList

class ForecastingDataSeeder {
    companion object {
        fun run(context: Context): ArrayList<Forecast> {
            val array = ArrayList<Forecast>()
//            var previousDate = DateTime()
//            for (i in 1..50) {
//               previousDate = previousDate.minusHours(2)
//                array.add(Forecast(previousDate, Weather(AirQualityIndex((10..300).random()), ((10..100).random()).toDouble(),  ((12..30).random()).toDouble(), ((60..150).random()).toDouble(), context.getString(R.string.weather_cloudy))))
//            }
            var nextDate = DateTime()
            val conditionList = ArrayList<String>()
            conditionList.add(context.getString(R.string.weather_sunny))
            conditionList.add(context.getString(R.string.weather_cloudy))
            conditionList.add(context.getString(R.string.weather_raining))
            for (i in 1..100) {
                array.add(Forecast(nextDate, Weather(AirQualityIndex((10..350).random()), ((10..100).random()).toDouble(), ((12..30).random()).toDouble(), ((60..150).random()).toDouble(), conditionList[(0..2).random()])))
                nextDate = nextDate.plusHours(2)
            }
            return array
        }
    }
}

